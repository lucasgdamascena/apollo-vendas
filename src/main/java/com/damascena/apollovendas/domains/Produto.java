package com.damascena.apollovendas.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @DecimalMin(value = "1.00")
    private BigDecimal preco;

    @JsonIgnore
    @NotNull
    @ManyToMany
    @JoinTable(name = "PRODUTO_CATEGORIA",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id.produto")
    private Set<ItemPedido> itensPedidos = new HashSet<>();

    @Deprecated
    public Produto() {
    }

    public Produto(String nome, BigDecimal preco) {
        validarArgumentos(nome, preco);
        this.nome = nome;
        this.preco = preco;
    }

    private void validarArgumentos(String nome, BigDecimal preco) {
        Assert.hasText(nome, "O argumento 'nome' deve ser preenchido.");
        Assert.isTrue(preco.compareTo(BigDecimal.valueOf(1.00)) != -1,
                "O argumento 'preco' deve ser maior que 1.00");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public Set<ItemPedido> getItensPedidos() {
        return itensPedidos;
    }

    @JsonIgnore
    public List<Pedido> getPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        for (ItemPedido itemPedido : itensPedidos) {
            pedidos.add(itemPedido.getPedido());
        }

        return pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id) && Objects.equals(nome, produto.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}