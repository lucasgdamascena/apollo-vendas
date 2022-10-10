package com.damascena.apollovendas.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AtualizarCategoriaRequest {

    @NotBlank(message = "Preenchimento obrigatório")
    @Size(min = 5, max = 30, message = "O tamanho deve ser entre 5 e 30 caracteres")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}