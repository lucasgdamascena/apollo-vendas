package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionar(@PathVariable("id") Long id) {
        Cliente cliente = service.selecionar(id);
        return ResponseEntity.ok().body(cliente);
    }
}