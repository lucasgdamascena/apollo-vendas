package com.damascena.apollovendas.controllers.exceptions;

import com.damascena.apollovendas.services.exceptions.AutorizacaoException;
import com.damascena.apollovendas.services.exceptions.IntegridadeVioladaException;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
                new MensagemPadrao(System.currentTimeMillis(),
                        HttpStatus.NOT_FOUND.value(), "Não encontrado",
                        objetoNaoEncontradoException.getMessage(),
                        httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemPadrao);
    }

    @ExceptionHandler(IntegridadeVioladaException.class)
    public ResponseEntity<MensagemPadrao> integridadeViolada(IntegridadeVioladaException integridadeVioladaException,
                                                             HttpServletRequest httpServletRequest) {
        MensagemPadrao mensagemPadrao =
                new MensagemPadrao(System.currentTimeMillis(),
                        HttpStatus.BAD_REQUEST.value(), "Integridade de dados",
                        integridadeVioladaException.getMessage(),
                        httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemPadrao);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemPadrao> argumentacaoInvalida(MethodArgumentNotValidException methodArgumentNotValidException,
                                                               HttpServletRequest httpServletRequest) {
        ValidacaoErro validacaoErro =
                new ValidacaoErro(System.currentTimeMillis(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação",
                        methodArgumentNotValidException.getMessage(),
                        httpServletRequest.getRequestURI());

        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            validacaoErro.adicionarErro(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validacaoErro);
    }

    @ExceptionHandler(AutorizacaoException.class)
    public ResponseEntity<MensagemPadrao> autorizacaoProibida(AutorizacaoException autorizacaoException,
                                                              HttpServletRequest httpServletRequest) {
        MensagemPadrao mensagemPadrao =
                new MensagemPadrao(System.currentTimeMillis(),
                        HttpStatus.FORBIDDEN.value(), "Acesso negado",
                        autorizacaoException.getMessage(),
                        httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mensagemPadrao);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MensagemPadrao> accessoNegado(AccessDeniedException accessDeniedException,
                                                        HttpServletRequest httpServletRequest) {

        MensagemPadrao mensagemPadrao
                = new MensagemPadrao(System.currentTimeMillis(),
                HttpStatus.FORBIDDEN.value(), "Acesso negado",
                accessDeniedException.getMessage(),
                httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mensagemPadrao);
    }
}