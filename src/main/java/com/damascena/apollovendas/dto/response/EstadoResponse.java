package com.damascena.apollovendas.dto.response;

import com.damascena.apollovendas.domains.Estado;

public class EstadoResponse {
    private Long id;
    private String nome;

    public EstadoResponse(Estado estado) {
        this.id = estado.getId();
        this.nome = estado.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}