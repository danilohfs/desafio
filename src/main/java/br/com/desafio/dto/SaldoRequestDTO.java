package br.com.desafio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaldoRequestDTO {

    private double valor;
    private String nomeDestino;

    //Criando os Getters e Setter da variavel valor
    public void setValor(double valor){
        this.valor = valor;
    }
    public double getValor(){
        return valor;
    }

    //Criando os Getters e Setter da variavel nomeDestino
    public void setNomeDestino(String nomeDestino){
        this.nomeDestino = nomeDestino;
    }
    public String getNomeDestino(){
        return nomeDestino;
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
