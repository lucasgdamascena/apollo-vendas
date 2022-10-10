package com.damascena.apollovendas.dto.response;

import com.damascena.apollovendas.domains.Produto;

import java.math.BigDecimal;

public class ListaProdutoResponse {

    private String nome;
    private BigDecimal preco;

    public ListaProdutoResponse(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
