package com.example.thechat.models;

import android.support.annotation.NonNull;

public class ChatUsuarios {

    private String idChat;
    private String IdUser;
    private Boolean status;

    public ChatUsuarios(){}

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return getIdChat();
    }
}
