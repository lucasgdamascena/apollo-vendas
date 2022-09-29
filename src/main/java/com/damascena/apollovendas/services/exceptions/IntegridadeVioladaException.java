package com.damascena.apollovendas.services.exceptions;

public class IntegridadeVioladaException extends RuntimeException {

    public IntegridadeVioladaException(String mensagem) {
        super(mensagem);
    }

    public IntegridadeVioladaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}