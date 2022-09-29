package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Pedido;
import com.damascena.apollovendas.repositories.PedidoRepository;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repositorio;

    public Pedido selecionar(Long id) {
        Optional<Pedido> pedido = repositorio.findById(id);
        return pedido.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Pedido.class));
    }
}