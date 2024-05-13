package br.com.desafio.service;

import br.com.desafio.config.exception.*;
import br.com.desafio.dto.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TransferenciaService {
    private static final String CLIENTE_API_URL = "http://localhost:9090/api/clientes/";
    private static final String CONTA_API_URL = "http://localhost:9090/api/contas/";
    private static final String NOTIFICACAO_API_URL = "http://localhost:9090/api/notificacoes/";

    private static RestTemplate restTemplate = new RestTemplate();

    @Value("${transferencia.maxTentativas}")
    private static int maxTentativas;

    @Value("${transferencia.sleepTentativas}")
    private static int sleepTentativas;

    @HystrixCommand(fallbackMethod = "fallbackPadrao")
    public static TransferenciaResponseDTO efetuarTransferencia(TransferenciaRequestDTO transferenciaRequestDTO) throws Exception {
        try {
            // Fazer uma requisição GET para a API de Cadastro para obter informações do cliente
            ClienteResponseDTO clienteResponseDTO = restTemplate.getForObject(CLIENTE_API_URL + transferenciaRequestDTO.getIdCliente(), ClienteResponseDTO.class);

            // Fazer uma requisição GET para a API de Cadastro para obter informações da conta do cliente
            ContaResponseDTO contaResponseDTO = restTemplate.getForObject(CONTA_API_URL + clienteResponseDTO.getId(), ContaResponseDTO.class);

            // Validar se a conta do cliente está ativa
            if (contaResponseDTO != null && contaResponseDTO.getAtivo()) {
                if (contaResponseDTO.getSaldo() > 0) {
                    if (contaResponseDTO.getLimiteDiario() == 0 || contaResponseDTO.getLimiteDiario() < transferenciaRequestDTO.getValor()) {
                        //tratamento para validação de limite diario, criada um exception para essa situação
                        throw new LimiteDiarioExcedidoException("Limite diário excedido para transferência");
                    } else {
                        return realizarTransferencia(transferenciaRequestDTO);
                    }
                } else {
                    //tratamento para validação de saldo insuficiente, criada um exception para essa situação
                    throw new SaldoInsuficienteException("A conta não possui saldo suficiente para realizar a transferência");
                }
            } else {
                // Lançar exceção se a conta estiver inativa
                throw new ContaInativaException("A conta do cliente está inativa");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new HttpClientErrorException(e.getStatusCode());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @HystrixCommand(fallbackMethod = "realizarTransferenciaFallback")
    private static TransferenciaResponseDTO realizarTransferencia(TransferenciaRequestDTO transferenciaRequestDTO) throws Exception {
        try {
            // Gerar ID de transferência
            UUID uuid = UUID.randomUUID();
            TransferenciaResponseDTO transferenciaResponseDTO = new TransferenciaResponseDTO();
            transferenciaResponseDTO.setIdTransferencia(uuid);

            // Criar a notificação para o Bacen
            NotificacaoRequestDTO notificacaoRequestDTO = new NotificacaoRequestDTO();
            notificacaoRequestDTO.setConta(new NotificacaoRequestDTO.Conta(transferenciaRequestDTO.getConta().getIdDestino(), transferenciaRequestDTO.getConta().getIdOrigem()));
            notificacaoRequestDTO.setValor(transferenciaRequestDTO.getValor());

            // Fazer a requisição POST para a API do Bacen, enviando a notificação
            ResponseEntity<NotificacaoRequestDTO> responseEntity = restTemplate.postForEntity(NOTIFICACAO_API_URL, notificacaoRequestDTO, NotificacaoRequestDTO.class);

            // Verificar o status code da resposta
            if (responseEntity.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {

            }
            return transferenciaResponseDTO;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private static TransferenciaResponseDTO realizarTransferenciaFallback(TransferenciaRequestDTO transferenciaRequestDTO) {
        // Lógica de fallback
        int tentativas = 0;
        while (tentativas < maxTentativas) { // Número máximo de tentativas
            try {
                // Espera exponencialmente crescente entre as tentativas
                TimeUnit.SECONDS.sleep((long) Math.pow(sleepTentativas, tentativas));

                // Tentativa de chamada novamente
                ResponseEntity<NotificacaoRequestDTO> responseEntity = restTemplate.postForEntity(NOTIFICACAO_API_URL, transferenciaRequestDTO, NotificacaoRequestDTO.class);

                // Verificar o status code da resposta
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    // Gerar ID de transferência
                    UUID uuid = UUID.randomUUID();
                    TransferenciaResponseDTO transferenciaResponseDTO = new TransferenciaResponseDTO();
                    transferenciaResponseDTO.setIdTransferencia(uuid);
                    return transferenciaResponseDTO;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            tentativas++;
        }
        // Caso todas as tentativas falhem, retorna um fallback padrão
        return fallbackPadrao();
    }

    private static TransferenciaResponseDTO fallbackPadrao() {
        // Fallback padrão
        TransferenciaResponseDTO fallbackResponse = new TransferenciaResponseDTO();
        fallbackResponse.setError(true);
        fallbackResponse.setMessage("Não foi possível concluir a transferência no momento. Por favor, tente novamente mais tarde.");
        return fallbackResponse;

    }
}
