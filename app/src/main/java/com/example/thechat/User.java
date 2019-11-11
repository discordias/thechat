package com.example.thechat;

public class User {
    private String email;
    private String nome;

    public User() {}

    public User(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
