package com.damascena.apollovendas.domains;

import com.damascena.apollovendas.domains.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Email
    private String email;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String documento;

    @Column(nullable = false)
    @NotNull
    private Integer tipoCliente;

    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "telefone")
    private Set<String> telefones = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    @Deprecated
    public Cliente() {
    }

    public Cliente(Long id, String nome, String email, String documento, TipoCliente tipoCliente) {
        validarArgumentos(nome, email, documento, tipoCliente);
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.tipoCliente = tipoCliente.getCodigo();
    }

    private void validarArgumentos(String nome, String email, String documento, TipoCliente tipoCliente) {
        Assert.hasText(nome, "O argumento 'nome' deve ser preenchido.");
        Assert.hasText(email, "O argumento 'email' deve ser preenchido.");
        Assert.hasText(documento, "O argumento 'documento' deve ser preenchido.");
        Assert.notNull(tipoCliente, "O argumento 'tipoCliente' não possui valor definido.");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public TipoCliente getTipoCliente() throws IllegalAccessException {
        return TipoCliente.toEnum(tipoCliente);
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(documento, cliente.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documento);
    }
}