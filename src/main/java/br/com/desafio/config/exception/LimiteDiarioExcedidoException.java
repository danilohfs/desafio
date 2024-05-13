package br.com.desafio.config.exception;

public class LimiteDiarioExcedidoException extends RuntimeException {

    public LimiteDiarioExcedidoException(String message) {
        super(message);
    }
}
