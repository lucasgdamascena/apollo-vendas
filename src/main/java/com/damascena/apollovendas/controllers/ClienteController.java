package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.dto.request.AtualizarClienteRequest;
import com.damascena.apollovendas.dto.request.CadastrarClienteRequest;
import com.damascena.apollovendas.services.ClienteService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionarPorId(@PathVariable("id") Long id) {
        Cliente cliente = clienteService.selecionarPorId(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity inserir(@RequestBody @Valid CadastrarClienteRequest cadastrarClienteRequest) {
        Cliente cliente = clienteService.inserir(cadastrarClienteRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id,
                                    @RequestBody @Valid AtualizarClienteRequest atualizarClienteRequest) {
        clienteService.atualizar(id, atualizarClienteRequest);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}