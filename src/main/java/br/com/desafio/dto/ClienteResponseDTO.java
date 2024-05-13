package br.com.desafio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDTO {

    private String id;
    private String nome;
    private String telefone;
    private String tipoPessoa;

    //Criando os Getters e Setter da variavel id
    public void setId(String id){
        this.id = id;
    }
    public String getId() {
        return id;
    }

    //Criando os Getters e Setter da variavel nome
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }

    //Criando os Getters e Setter da variavel telefone
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public String getTelefone(){
        return telefone;
    }

    //Criando os Getters e Setter da variavel tipoPessoa
    public void setTipoPessoa(String tipoPessoa){
        this.tipoPessoa = tipoPessoa;
    }
    public String getTipoPessoa(){
        return tipoPessoa;
    }
}
