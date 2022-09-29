package com.damascena.apollovendas.domains;

import com.damascena.apollovendas.domains.enums.EstadoPagamento;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PagamentoComBoleto extends Pagamento {

    @Column(nullable = true)
    private Date dataVencimento;

    @Column(nullable = true)
    private Date dataPagamento;

    @Deprecated
    public PagamentoComBoleto() {
    }

    public PagamentoComBoleto(Long id, EstadoPagamento estadoPagamento, Pedido pedido,
                              Date dataVencimento, Date dataPagamento) {
        super(id, estadoPagamento, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }
}