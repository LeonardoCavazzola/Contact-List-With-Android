package com.example.androidt2.model;

import android.graphics.Bitmap;

public class Contact {

    private int id;
    private String nome;
    private String telefone;
    private Bitmap foto;
    private String observacao;

    public Contact(String nome, String telefone, Bitmap foto, String observacao) {
        this.nome = nome;
        this.telefone = telefone;
        this.foto = foto;
        this.observacao = observacao;
    }

    public Contact(int id, String nome, String telefone, Bitmap foto, String observacao) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.foto = foto;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public String getObservacao() {
        return observacao;
    }
}
