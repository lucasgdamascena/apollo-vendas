package com.damascena.apollovendas.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoErro extends MensagemPadrao {

    private List<MensagemCampo> erros = new ArrayList<>();

    public ValidacaoErro(Integer status, String mensagem, Long timestamp) {
        super(status, mensagem, timestamp);
    }

    public List<MensagemCampo> getErros() {
        return erros;
    }

    public void adicionarErro(String nomeCampo, String mensagem) {
        erros.add(new MensagemCampo(nomeCampo, mensagem));
    }
}