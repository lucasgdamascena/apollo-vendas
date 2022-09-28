package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.repositories.ClienteRepository;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repositorio;

    public Cliente selecionar(Long id) {
        Optional<Cliente> cliente = repositorio.findById(id);
        return cliente.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Cliente.class));
    }
}