package com.damascena.apollovendas.dto.response;

import com.damascena.apollovendas.domains.Cidade;

public class CidadeResponse {
    private Long id;
    private String nome;

    public CidadeResponse(Cidade cidade) {
        this.id = cidade.getId();
        this.nome = cidade.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}