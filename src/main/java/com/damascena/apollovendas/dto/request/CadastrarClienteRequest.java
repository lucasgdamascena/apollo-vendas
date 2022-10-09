package com.damascena.apollovendas.dto.request;

import com.damascena.apollovendas.domains.Cidade;
import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.domains.Endereco;
import com.damascena.apollovendas.domains.enums.TipoCliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

public class CadastrarClienteRequest {

    @NotBlank(message = "Preenchimento obrigatório")
    @Size(min = 3, max = 120, message = "O tamanho deve ser entre 3 e 120 caracteres")
    private String nome;

    @NotBlank(message = "Preenchimento obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Preenchimento obrigatório")
    private String documento;

    @NotNull(message = "Valor Inválido")
    private Integer tipoCliente;

    @NotBlank(message = "Preenchimento obrigatório")
    private String logradouro;

    @NotBlank
    private String numero;

    private String complemento;

    @NotBlank(message = "Preenchimento obrigatório")
    private String bairro;

    @NotBlank(message = "Preenchimento obrigatório")
    private String cep;

    @NotBlank(message = "Preenchimento obrigatório")
    private String telefone1;

    private String telefone2;
    private String telefone3;

    @NotNull(message = "Valor Inválido")
    private Long cidadeId;

    public CadastrarClienteRequest(String nome, String email, String documento, Integer tipoCliente, String logradouro,
                                   String numero, String complemento, String bairro, String cep, String telefone1,
                                   String telefone2, String telefone3, Long cidadeId) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.tipoCliente = tipoCliente;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.telefone3 = telefone3;
        this.cidadeId = cidadeId;
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

    public Integer getTipoCliente() {
        return tipoCliente;
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

    public String getTelefone1() {
        return telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public Long getCidadeId() {
        return cidadeId;
    }

    public Cliente toCliente(Optional<Cidade> cidade) {

        Cliente cliente = null;

        try {
            cliente = new Cliente(nome, email, documento, TipoCliente.toEnum(tipoCliente));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Endereco endereco = new Endereco(logradouro, numero, complemento,
                bairro, cep, cliente, cidade.get());

        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(telefone1);

        if (telefone2 != null) {
            cliente.getTelefones().add(telefone2);
        }

        if (telefone3 != null) {
            cliente.getTelefones().add(telefone3);
        }

        return cliente;
    }
}