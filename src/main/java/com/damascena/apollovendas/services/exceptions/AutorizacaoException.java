package com.damascena.apollovendas.services.exceptions;

public class AutorizacaoException extends RuntimeException {

    public AutorizacaoException(String mensagem) {
        super(mensagem);
    }

    public AutorizacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}