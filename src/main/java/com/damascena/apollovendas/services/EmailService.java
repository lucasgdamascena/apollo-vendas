package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Cliente;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void confirmarCliente(Cliente cliente);

    void enviar(SimpleMailMessage simpleMailMessage);
}