package br.com.desafio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaResponseDTO {

    private String id;
    private double saldo;
    private double limiteDiario;
    private boolean ativo;

    //Criando os Getters e Setter da variavel id
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }

    //Criando os Getters e Setter da variavel saldo
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }
    public double getSaldo(){
        return saldo;
    }

    //Criando os Getters e Setter da variavel limiteDiario
    public void setLimiteDiario(double limiteDiario){
        this.limiteDiario = limiteDiario;
    }
    public double getLimiteDiario(){
        return limiteDiario;
    }

    //Criando os Getters e Setter da variavel ativo
    public void  setAtivo(boolean ativo){
        this.ativo = ativo;
    }
    public boolean getAtivo(){
        return ativo;
    }
}
