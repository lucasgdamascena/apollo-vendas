package com.damascena.apollovendas.controllers.exceptions;

import com.damascena.apollovendas.services.exceptions.IntegridadeVioladaException;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<MensagemPadrao> objetoNaoEncontrado(ObjetoNaoEncontradoException exception,
                                                              HttpServletRequest request) {
        MensagemPadrao mensagemPadrao =
                new MensagemPadrao(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemPadrao);
    }

    @ExceptionHandler(IntegridadeVioladaException.class)
    public ResponseEntity<MensagemPadrao> integridadeViolada(IntegridadeVioladaException exception,
                                                             HttpServletRequest request) {
        MensagemPadrao mensagemPadrao =
                new MensagemPadrao(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemPadrao);
    }
}