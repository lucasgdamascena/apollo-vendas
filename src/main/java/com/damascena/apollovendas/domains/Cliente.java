package com.damascena.apollovendas.domains;

import com.damascena.apollovendas.domains.enums.Perfil;
import com.damascena.apollovendas.domains.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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
    @JsonIgnore
    private String senha;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String documento;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Max(value = 2)
    private Integer tipoCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "telefone")
    private Set<String> telefones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis")
    private Set<Integer> perfis = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    @Deprecated
    public Cliente() {
        adicionarPerfil(Perfil.CLIENTE);
    }

    public Cliente(String nome, String email, String senha, String documento, TipoCliente tipoCliente) {
        validarArgumentos(nome, email, senha, documento, tipoCliente);
        this.nome = nome;
        this.email = email;
        this.senha = new BCryptPasswordEncoder().encode(senha);
        this.documento = documento;
        this.tipoCliente = tipoCliente.getCodigo();
        adicionarPerfil(Perfil.CLIENTE);
    }

    private void validarArgumentos(String nome, String email, String senha, String documento, TipoCliente tipoCliente) {
        Assert.hasText(nome, "O argumento 'nome' deve ser preenchido.");
        Assert.hasText(email, "O argumento 'email' deve ser preenchido.");
        Assert.hasText(senha, "O argumento 'senha' deve ser preenchido.");
        Assert.hasText(documento, "O argumento 'documento' deve ser preenchido.");
        Assert.notNull(tipoCliente, "O argumento 'tipoCliente' não possui valor definido.");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
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

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void adicionarPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append(nome).append('\'');
        sb.append(", email='").append(email).append('\'');

        for (String telefone : this.telefones) {
            sb.append(", telefone=").append(telefone);
        }

        for (Endereco endereco : this.enderecos) {
            sb.append(", endereco=").append(endereco.toString());
        }

        return sb.toString();
    }
}