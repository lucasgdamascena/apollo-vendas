package com.damascena.apollovendas.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Estado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "estado")
    private List<Cidade> cidades = new ArrayList<>();

    @Deprecated
    public Estado() {
    }

    public Estado(Long id, String nome) {
        validarArgumento(nome);
        this.id = id;
        this.nome = nome;
    }

    private void validarArgumento(String nome) {
        Assert.hasText(nome, "O argumento 'nome' deve ser preenchido.");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estado estado = (Estado) o;
        return Objects.equals(id, estado.id) && Objects.equals(nome, estado.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}