package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService servico;

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionar(@PathVariable("id") Long id) {
        Categoria categoria = servico.selecionar(id);
        return ResponseEntity.ok().body(categoria);
    }
}