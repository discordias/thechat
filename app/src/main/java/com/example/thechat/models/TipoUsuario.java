package com.example.thechat.models;

public enum TipoUsuario {

    PROFESSOR("professor"), ALUNO("aluno"), ADMINISTRADOR("admin");

    public String tipo;

    TipoUsuario(String tipoValor){
        tipo = tipoValor;
    }

    public String getTipo(){
        return tipo;
    }

}
