package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.dto.request.AtualizarClienteRequest;
import com.damascena.apollovendas.dto.request.CadastrarClienteRequest;
import com.damascena.apollovendas.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService servico;

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionarPorId(@PathVariable("id") Long id) {
        Cliente cliente = servico.selecionarPorId(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity inserir(@RequestBody @Valid CadastrarClienteRequest request) {
        Cliente cliente = servico.inserir(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody @Valid AtualizarClienteRequest request) {
        servico.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}