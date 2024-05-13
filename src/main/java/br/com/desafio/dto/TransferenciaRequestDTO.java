package br.com.desafio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaRequestDTO {

    private String idCliente;
    private double valor;
    private Conta conta;

    //Criando os Getters e Setter da variavel idCLiente
    public String getIdCliente(){
        return idCliente;
    }
    public void setIdCliente(String idCliente){
        this.idCliente = idCliente;
    }

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

        //Criando os Getters e Setter da variavel idOrigem
        public void setIdOrigem(String idOrigem){
            this.idOrigem = idOrigem;
        }
        public String getIdOrigem(){
            return idOrigem;
        }

        //Criando os Getters e Setter da variavel idDestino
        public void setIdDestino(String idDestino){
            this.idDestino = idDestino;
        }
        public String getIdDestino(){
            return idDestino;
        }
    }
}