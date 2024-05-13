package br.com.desafio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacaoRequestDTO {
    private double valor;
    private Conta conta;

    //Criando os Getters e Setter da variavel valor
    public void setValor(double valor){
        this.valor = valor;
    }
    public double getValor(){
        return valor;
    }

    //Criando os Getters e Setter da variavel conta
    public void setConta(Conta conta){
        this.conta = conta;
    }
    public Conta getConta(){
        return conta;
    }

    public static class Conta {
        private String idOrigem;
        private String idDestino;

        //Criando Construtor para poder setar os valores
        public Conta(String idOrigem, String idDestino){
            this.idOrigem = idOrigem;
            this.idDestino = idDestino;
        }


        //Criando os Getters e Setter da variavel idOrigem
        public void setIdOrigem(String idOrigem){
            this.idOrigem = idOrigem;
        }
        public String getIdOrigem(){
            return idOrigem;
        }

        //Criando os Getters e Setter da variavel idDestino
        public Conta setIdDestino(String idDestino){
            this.idDestino = idDestino;
            return null;
        }
        public String getIdDestino(){
            return idDestino;
        }
    }
}
