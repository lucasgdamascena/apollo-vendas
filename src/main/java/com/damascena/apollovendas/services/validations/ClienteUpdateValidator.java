package com.damascena.apollovendas.services.validations;

import com.damascena.apollovendas.controllers.exceptions.MensagemCampo;
import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.dto.request.AtualizarClienteRequest;
import com.damascena.apollovendas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, AtualizarClienteRequest> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void initialize(ClienteUpdate clienteUpdate) {
    }

    @Override
    public boolean isValid(AtualizarClienteRequest atualizarClienteRequest, ConstraintValidatorContext context) {
        List<MensagemCampo> lista = new ArrayList<>();

        lista = validacaoEmail(atualizarClienteRequest, lista);

        for (MensagemCampo mensagemCampo : lista) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(mensagemCampo.getMensagem())
                    .addPropertyNode(mensagemCampo.getNomeCampo()).addConstraintViolation();
        }

        return lista.isEmpty();
    }

    private List<MensagemCampo> validacaoEmail(AtualizarClienteRequest atualizarClienteRequest, List<MensagemCampo> lista) {

        Map<String, String> mapeamentoHttp =
                (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long urlID = Long.parseLong(mapeamentoHttp.get("id"));

        Cliente cliente = clienteRepository.findByEmail(atualizarClienteRequest.getEmail());

        if (cliente != null && !cliente.getId().equals(urlID)) {
            lista.add(new MensagemCampo("email", "E-mail cadastrado anteriormente"));
        }

        return lista;
    }
}