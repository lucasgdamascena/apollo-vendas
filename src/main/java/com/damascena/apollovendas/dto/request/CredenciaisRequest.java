package com.damascena.apollovendas.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CredenciaisRequest {
    @NotBlank(message = "Preenchimento Obrigatório")
    @Email
    private String email;

    @NotBlank(message = "Preenchimento Obrigatório")
    private String senha;

    public CredenciaisRequest() {
    }

    public CredenciaisRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}