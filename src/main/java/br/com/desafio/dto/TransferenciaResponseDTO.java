package br.com.desafio.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransferenciaResponseDTO {
    private UUID idTransferencia;
    private String message;
    private boolean error;

    //Criando os Getters e Setter da variavel idTransferencia
    public void setIdTransferencia(UUID idTransferencia) {
        this.idTransferencia = idTransferencia;
    }
    public UUID getIdTransferencia(){
        return idTransferencia;
    }

    //Criando os Getters e Setter da variavel error
    public void setError(boolean error) {
        this.error = error;
    }
    public boolean getError(){
        return error;
    }

    //Criando os Getters e Setter da variavel message
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
