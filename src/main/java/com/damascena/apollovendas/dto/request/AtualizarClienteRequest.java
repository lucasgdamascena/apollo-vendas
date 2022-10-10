package com.damascena.apollovendas.dto.request;

import com.damascena.apollovendas.services.validations.ClienteUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ClienteUpdate
public class AtualizarClienteRequest {

    @NotBlank(message = "Preenchimento obrigatório")
    @Size(min = 3, max = 120, message = "O tamanho deve ser entre 3 e 120 caracteres")
    private String nome;

    @NotBlank(message = "Preenchimento obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    public AtualizarClienteRequest(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}