package com.example.thechat.models;

import android.support.annotation.NonNull;

public class Mensagem {

    private String idUsuario;
    private String mensagem;


    public Mensagem() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @NonNull
    @Override
    public String toString() {
        return this.mensagem;
    }
}
