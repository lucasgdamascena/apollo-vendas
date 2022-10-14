package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.remetente}")
    private String remetente;

    @Override
    public void confirmarCliente(Cliente cliente) {
        SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromPedido(cliente);
        enviar(simpleMailMessage);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Cliente cliente) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(cliente.getEmail());
        simpleMailMessage.setFrom(this.remetente);
        simpleMailMessage.setSubject("Confirmação de Conta");
        simpleMailMessage.setText("Conta Criada com Sucesso! " + cliente);

        return simpleMailMessage;
    }
}