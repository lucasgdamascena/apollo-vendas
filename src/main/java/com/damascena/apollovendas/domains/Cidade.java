package com.damascena.apollovendas.domains;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Cidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    public Cidade() {
    }

    public Cidade(Long id, String nome, Estado estado) {
        validarArgumentos(nome, estado);
        this.id = id;
        this.nome = nome;
        this.estado = estado;
    }

    private void validarArgumentos(String nome, Estado estado) {
        Assert.hasText(nome, "O argumento 'nome' deve ser preenchido.");
        Assert.notNull(estado, "O argumento 'estado' não possui valor definido.");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Estado getEstado() {
        return estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cidade cidade = (Cidade) o;
        return Objects.equals(id, cidade.id) && Objects.equals(nome, cidade.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}