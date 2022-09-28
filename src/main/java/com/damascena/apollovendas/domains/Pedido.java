package com.damascena.apollovendas.domains;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime instante;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public Pedido() {
    }

    public Pedido(Long id, Cliente cliente, Endereco endereco) {
        validarArgumentos(cliente, endereco);
        this.id = id;
        this.instante = LocalDateTime.now();
        this.cliente = cliente;
        this.endereco = endereco;
    }

    private void validarArgumentos(Cliente cliente, Endereco endereco) {
        Assert.notNull(cliente, "O argumento 'cliente' não possui valor definido.");
        Assert.notNull(endereco, "O argumento 'endereco' não possui valor definido.");
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}