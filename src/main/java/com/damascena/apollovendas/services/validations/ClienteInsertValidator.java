package com.damascena.apollovendas.services.validations;

import com.damascena.apollovendas.controllers.exceptions.MensagemCampo;
import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.domains.enums.TipoCliente;
import com.damascena.apollovendas.dto.request.CadastrarClienteRequest;
import com.damascena.apollovendas.repositories.ClienteRepository;
import com.damascena.apollovendas.services.validations.utils.IdentificacaoNacional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, CadastrarClienteRequest> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert clienteInsert) {
    }

    @Override
    public boolean isValid(CadastrarClienteRequest cadastrarClienteRequest, ConstraintValidatorContext context) {
        List<MensagemCampo> lista = new ArrayList<>();

        lista = validacaoDocumento(cadastrarClienteRequest, lista);

        lista = validacaoEmail(cadastrarClienteRequest, lista);

        for (MensagemCampo mensagemCampo : lista) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(mensagemCampo.getMensagem())
                    .addPropertyNode(mensagemCampo.getNomeCampo()).addConstraintViolation();
        }

        return lista.isEmpty();
    }

    private List<MensagemCampo> validacaoDocumento(CadastrarClienteRequest cadastrarClienteRequest, List<MensagemCampo> lista) {
        Cliente cliente = clienteRepository.findByDocumento(cadastrarClienteRequest.getDocumento());

        if (cliente != null) {
            lista.add(new MensagemCampo("documento", "Documento cadastrado anteriormente"));
        }

        if (cadastrarClienteRequest.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCodigo()) &&
                !IdentificacaoNacional.isValidCPF(cadastrarClienteRequest.getDocumento())) {
            lista.add(new MensagemCampo("documento", "CPF Inválido"));
        }

        if (cadastrarClienteRequest.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) &&
                !IdentificacaoNacional.isValidCNPJ(cadastrarClienteRequest.getDocumento())) {
            lista.add(new MensagemCampo("documento", "CNPJ Inválido"));
        }

        return lista;
    }

    private List<MensagemCampo> validacaoEmail(CadastrarClienteRequest cadastrarClienteRequest, List<MensagemCampo> lista) {
        Cliente cliente = clienteRepository.findByEmail(cadastrarClienteRequest.getEmail());

        if (cliente != null) {
            lista.add(new MensagemCampo("email", "E-mail cadastrado anteriormente"));
        }

        return lista;
    }
}