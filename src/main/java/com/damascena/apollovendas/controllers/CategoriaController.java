package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.dto.request.CategoriaRequest;
import com.damascena.apollovendas.dto.response.ListaCategoriaResponse;
import com.damascena.apollovendas.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService servico;

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionarPorId(@PathVariable("id") Long id) {
        Categoria categoria = servico.selecionarPorId(id);
        return ResponseEntity.ok().body(categoria);
    }

    @PostMapping
    public ResponseEntity inserir(@RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = servico.inserir(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaRequest request) {
        servico.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ListaCategoriaResponse>> selecionarTodos() {
        List<ListaCategoriaResponse> listaCategoriaResponses = servico.selecionarTodos();
        return ResponseEntity.ok().body(listaCategoriaResponses);
    }
}