package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Pedido;
import com.damascena.apollovendas.repositories.PedidoRepository;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido selecionarPorId(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Pedido.class));
    }
}