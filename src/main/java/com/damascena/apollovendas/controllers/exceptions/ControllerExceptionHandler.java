package com.damascena.apollovendas.controllers.exceptions;

import com.damascena.apollovendas.services.exceptions.IntegridadeVioladaException;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<MensagemPadrao> objetoNaoEncontrado(ObjetoNaoEncontradoException objetoNaoEncontradoException,
                                                              HttpServletRequest httpServletRequest) {
        MensagemPadrao mensagemPadrao =
                new MensagemPadrao(HttpStatus.NOT_FOUND.value(),
                        objetoNaoEncontradoException.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemPadrao);
    }

    @ExceptionHandler(IntegridadeVioladaException.class)
    public ResponseEntity<MensagemPadrao> integridadeViolada(IntegridadeVioladaException integridadeVioladaException,
                                                             HttpServletRequest httpServletRequest) {
        MensagemPadrao mensagemPadrao =
                new MensagemPadrao(HttpStatus.BAD_REQUEST.value(),
                        integridadeVioladaException.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemPadrao);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemPadrao> argumentacaoInvalida(MethodArgumentNotValidException methodArgumentNotValidException,
                                                               HttpServletRequest httpServletRequest) {
        ValidacaoErro validacaoErro =
                new ValidacaoErro(HttpStatus.BAD_REQUEST.value(), "Erro de Validação", System.currentTimeMillis());

        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            validacaoErro.adicionarErro(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validacaoErro);
    }
}