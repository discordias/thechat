package com.example.thechat.models;

import com.example.thechat.config.Conexao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

public class Usuario {

    private String id;
    private String email;
    private String nome;
    private TipoUsuario tipo;
    private String senha;

    public Usuario() {}

    public void salvarUsuario(){

        DatabaseReference myRef = Conexao.getFirebase();
        myRef.child("users").child(this.getId()).setValue(this);

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public static String tituloCadastro(String tipo){

        if(TipoUsuario.ALUNO.getTipo().equals(tipo)){
            return "Novo Cadastro de Aluno";
        }else if(TipoUsuario.PROFESSOR.getTipo().equals(tipo)){
            return "Novo Cadastro de Professor";
        }else{
            return "Novo Cadastro";
        }
    }
}
