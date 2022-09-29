package com.damascena.apollovendas.dto.response;

import com.damascena.apollovendas.domains.Categoria;

public class ListaCategoriaResponse {

    private Long id;
    private String nome;

    public ListaCategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}