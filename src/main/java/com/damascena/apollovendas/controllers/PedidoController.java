package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.domains.Pedido;
import com.damascena.apollovendas.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService servico;

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionar(@PathVariable("id") Long id) {
        Pedido pedido = servico.selecionar(id);
        return ResponseEntity.ok().body(pedido);
    }
}