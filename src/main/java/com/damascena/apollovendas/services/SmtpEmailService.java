package com.damascena.apollovendas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/*
    Classe Trabalha com Autenticação no Google, talvez seja necessário seguir os passos abaixo:
    https://support.google.com/accounts/answer/185833
 */
public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void enviar(SimpleMailMessage simpleMailMessage) {
        LOG.info("Enviando envio de e-mail");
        mailSender.send(simpleMailMessage);
        LOG.info("E-mail enviado com sucesso!");
    }
}