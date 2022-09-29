package com.damascena.apollovendas.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String logradouro;

    @Column(nullable = false)
    @NotBlank
    private String numero;

    @Column(nullable = false)
    @NotBlank
    private String complemento;

    @Column(nullable = false)
    @NotBlank
    private String bairro;

    @Column(nullable = false)
    @NotBlank
    private String cep;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @Deprecated
    public Endereco() {
    }

    public Endereco(Long id, String logradouro, String numero, String complemento,
                    String bairro, String cep, Cliente cliente, Cidade cidade) {
        validarArgumentos(logradouro, numero, complemento, bairro, cep, cliente, cidade);
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cliente = cliente;
        this.cidade = cidade;
    }

    private void validarArgumentos(String logradouro, String numero, String complemento,
                                   String bairro, String cep, Cliente cliente, Cidade cidade) {
        Assert.hasText(logradouro, "O argumento 'logradouro' deve ser preenchido.");
        Assert.hasText(numero, "O argumento 'numero' deve ser preenchido.");
        Assert.hasText(complemento, "O argumento 'complemento' deve ser preenchido.");
        Assert.hasText(bairro, "O argumento 'bairro' deve ser preenchido.");
        Assert.hasText(cep, "O argumento 'cep' deve ser preenchido.");
        Assert.notNull(cliente, "O argumento 'cliente' não possui valor definido.");
        Assert.notNull(cidade, "O argumento 'cidade' não possui valor definido.");
    }

    public Long getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Cidade getCidade() {
        return cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}