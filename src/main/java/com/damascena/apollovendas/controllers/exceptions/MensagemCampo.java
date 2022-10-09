package com.damascena.apollovendas.controllers.exceptions;

import java.io.Serializable;

public class MensagemCampo implements Serializable {

    private String nomeCampo;
    private String mensagem;

    public MensagemCampo() {
    }

    public MensagemCampo(String nomeCampo, String mensagem) {
        this.nomeCampo = nomeCampo;
        this.mensagem = mensagem;
    }

    public String getNomeCampo() {
        return nomeCampo;
    }

    public String getMensagem() {
        return mensagem;
    }
}