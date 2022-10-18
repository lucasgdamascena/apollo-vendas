package com.damascena.apollovendas.domains.enums;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(2, "Cancelado");

    private Integer codigo;
    private String descricao;

    private EstadoPagamento(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static EstadoPagamento toEnum(Integer codigo) {

        if (codigo != null) {
            for (EstadoPagamento estadoPagamento : EstadoPagamento.values()) {
                if (codigo.equals(estadoPagamento.getCodigo())) {
                    return estadoPagamento;
                }
            }
        }

        return null;
    }
}