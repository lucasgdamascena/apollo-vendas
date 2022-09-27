package com.damascena.apollovendas.controllers.exceptions;

import java.io.Serializable;

public class MensagemPadrao implements Serializable {

    private Integer status;
    private String mensagem;
    private Long timestamp;

    public MensagemPadrao(Integer status, String mensagem, Long timestamp) {
        this.status = status;
        this.mensagem = mensagem;
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}