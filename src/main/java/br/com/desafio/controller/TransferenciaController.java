package br.com.desafio.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.desafio.dto.TransferenciaRequestDTO;
import br.com.desafio.dto.TransferenciaResponseDTO;
import br.com.desafio.service.TransferenciaService;

@RestController
public class TransferenciaController {

    @PostMapping("/transferencia")
    public ResponseEntity<TransferenciaResponseDTO> efetuarTransferencia(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) throws Exception {
        // Chamada do service, para deixar a regra de negocio na camada de Service
        TransferenciaResponseDTO transferenciaResponseDTO = TransferenciaService.efetuarTransferencia(transferenciaRequestDTO);

        return ResponseEntity.ok().body(transferenciaResponseDTO);
    }
}
