package br.com.desafio;

import br.com.desafio.config.exception.ContaInativaException;
import br.com.desafio.dto.*;
import br.com.desafio.service.TransferenciaService;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApiTransferenciaApplicationTests {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TransferenciaService transferenciaService;

    @Test
    public void testEfetuarTransferenciaSucesso() throws Exception {
        // Mock da resposta da API de clientes
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId("1");

        // Mock da resposta da API de contas
        ContaResponseDTO contaResponseDTO = new ContaResponseDTO();
        contaResponseDTO.setAtivo(true);
        contaResponseDTO.setSaldo(200);
        contaResponseDTO.setLimiteDiario(300);

        // Configurar comportamento do mock para as chamadas da API
        when(restTemplate.getForObject(anyString(), eq(ClienteResponseDTO.class))).thenReturn(clienteResponseDTO);
        when(restTemplate.getForObject(anyString(), eq(ContaResponseDTO.class))).thenReturn(contaResponseDTO);
        when(restTemplate.postForEntity(anyString(), any(), eq(NotificacaoRequestDTO.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        // Chamar o método a ser testado
        TransferenciaResponseDTO responseDTO = transferenciaService.efetuarTransferencia(new TransferenciaRequestDTO());

        // Verificar o resultado
        assertEquals(responseDTO.getIdTransferencia().getClass(), java.util.UUID.class);
    }

    @Test
    public void testEfetuarTransferenciaContaInativa() throws Exception {
        // Mock da resposta da API de clientes
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId("1");

        // Mock da resposta da API de contas
        ContaResponseDTO contaResponseDTO = new ContaResponseDTO();
        contaResponseDTO.setAtivo(false);

        // Configurar comportamento do mock para as chamadas da API
        when(restTemplate.getForObject(anyString(), eq(ClienteResponseDTO.class))).thenReturn(clienteResponseDTO);
        when(restTemplate.getForObject(anyString(), eq(ContaResponseDTO.class))).thenReturn(contaResponseDTO);

        // Chamar o método a ser testado
        transferenciaService.efetuarTransferencia(new TransferenciaRequestDTO());
    }

    @Test
    public void testEfetuarTransferenciaClienteNaoEncontrado() throws Exception {
        // Configurar comportamento do mock para a chamada da API de clientes
        when(restTemplate.getForObject(anyString(), eq(ClienteResponseDTO.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Chamar o método a ser testado
        transferenciaService.efetuarTransferencia(new TransferenciaRequestDTO());
    }

    @Test
    public void testFallback() throws Exception {
        // Configurar comportamento do mock para lançar uma exceção durante a chamada da API externa
        when(restTemplate.postForEntity(anyString(), any(), any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // Chamar o método a ser testado
        TransferenciaResponseDTO responseDTO = transferenciaService.efetuarTransferencia(new TransferenciaRequestDTO());

        // Verificar se o fallback foi acionado corretamente
        assertEquals("Não foi possível concluir a transferência no momento. Por favor, tente novamente mais tarde.", responseDTO.getMessage());
    }
}
