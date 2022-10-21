package com.damascena.apollovendas.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoErro extends MensagemPadrao {

    private List<MensagemCampo> erros = new ArrayList<>();

    public ValidacaoErro(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<MensagemCampo> getErros() {
        return erros;
    }

    public void adicionarErro(String nomeCampo, String mensagem) {
        erros.add(new MensagemCampo(nomeCampo, mensagem));
    }
}