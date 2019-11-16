package com.example.thechat.models;

public enum TipoUsuario {

    PROFESSOR("PROFESSOR"), ALUNO("ALUNO"), ADMIN("ADMIN");

    public String tipo;

    TipoUsuario(String tipoValor){
        tipo = tipoValor;
    }

    public String getTipo(){
        return tipo;
    }

}
