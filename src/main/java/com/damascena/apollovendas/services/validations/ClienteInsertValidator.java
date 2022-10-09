package com.damascena.apollovendas.services.validations;

import com.damascena.apollovendas.controllers.exceptions.MensagemCampo;
import com.damascena.apollovendas.domains.enums.TipoCliente;
import com.damascena.apollovendas.dto.request.CadastrarClienteRequest;
import com.damascena.apollovendas.services.validations.utils.IdentificacaoNacional;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, CadastrarClienteRequest> {
    @Override
    public void initialize(ClienteInsert clienteInsert) {
    }

    @Override
    public boolean isValid(CadastrarClienteRequest cadastrarClienteRequest, ConstraintValidatorContext context) {
        List<MensagemCampo> lista = new ArrayList<>();

        if (cadastrarClienteRequest.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCodigo()) &&
                !IdentificacaoNacional.isValidCPF(cadastrarClienteRequest.getDocumento())) {
            lista.add(new MensagemCampo("documento", "CPF Inválido"));
        }

        if (cadastrarClienteRequest.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) &&
                !IdentificacaoNacional.isValidCNPJ(cadastrarClienteRequest.getDocumento())) {
            lista.add(new MensagemCampo("documento", "CNPJ Inválido"));
        }

        for (MensagemCampo mensagemCampo : lista) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(mensagemCampo.getMensagem())
                    .addPropertyNode(mensagemCampo.getNomeCampo()).addConstraintViolation();
        }

        return lista.isEmpty();
    }
}