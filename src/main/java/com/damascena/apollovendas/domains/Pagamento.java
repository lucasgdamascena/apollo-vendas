package com.damascena.apollovendas.domains;

import com.damascena.apollovendas.domains.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {

    @Id
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Max(value = 2)
    private Integer estadoPagamento;

    @JsonIgnore
    @NotNull
    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

    @Deprecated
    public Pagamento() {
    }

    public Pagamento(EstadoPagamento estadoPagamento, Pedido pedido) {
        validarArgumentos(estadoPagamento, pedido);
        this.estadoPagamento = estadoPagamento.getCodigo();
        this.pedido = pedido;
    }

    private void validarArgumentos(EstadoPagamento estadoPagamento, Pedido pedido) {
        Assert.notNull(estadoPagamento, "O argumento 'estadoPagamento' não possui valor definido.");
        Assert.notNull(pedido, "O argumento 'pedido' não possui valor definido.");
    }

    public Long getId() {
        return id;
    }

    public EstadoPagamento getEstadoPagamento() {
        return EstadoPagamento.toEnum(estadoPagamento);
    }

    public Pedido getPedido() {
        return pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}