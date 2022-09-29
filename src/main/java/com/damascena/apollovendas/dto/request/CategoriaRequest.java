package com.damascena.apollovendas.dto.request;

import com.damascena.apollovendas.domains.Categoria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoriaRequest {

    @NotBlank
    @Size(max = 50)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria toCategoria() {
        return new Categoria(this.nome);
    }
}