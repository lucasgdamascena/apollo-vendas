package com.damascena.apollovendas.domains;

import com.damascena.apollovendas.domains.enums.EstadoPagamento;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PagamentoComCartao extends Pagamento {

    @Column(nullable = true)
    private Integer numeroDeParcelas;

    @Deprecated
    public PagamentoComCartao() {
    }

    public PagamentoComCartao(EstadoPagamento estadoPagamento, Pedido pedido, Integer numeroDeParcelas) {
        super(estadoPagamento, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }
}