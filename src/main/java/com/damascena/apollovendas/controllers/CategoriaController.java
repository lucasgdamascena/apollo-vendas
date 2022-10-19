package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.dto.request.AtualizarCategoriaRequest;
import com.damascena.apollovendas.dto.request.CadastrarCategoriaRequest;
import com.damascena.apollovendas.dto.response.ListaCategoriaResponse;
import com.damascena.apollovendas.services.CategoriaService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
@Validated
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<ListaCategoriaResponse>> selecionar() {
        List<ListaCategoriaResponse> listaCategoriaResponses = categoriaService.selecionar();
        return ResponseEntity.ok().body(listaCategoriaResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionarPorId(@PathVariable("id") Long id) {
        Categoria categoria = categoriaService.selecionarPorId(id);
        return ResponseEntity.ok().body(categoria);
    }

    @GetMapping(value = "/pagina")
    public ResponseEntity<Page<ListaCategoriaResponse>> selecionarPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                                                                           @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
                                                                           @RequestParam(value = "direcao", defaultValue = "ASC") String direcao,
                                                                           @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor) {
        Page<Categoria> categoriasPaginadas = categoriaService.selecionarPaginado(pagina, linhasPorPagina, direcao, ordenarPor);
        Page<ListaCategoriaResponse> categoriasPaginadasResponse =
                categoriasPaginadas.map(obj -> new ListaCategoriaResponse(obj));
        return ResponseEntity.ok().body(categoriasPaginadasResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity inserir(@RequestBody @Valid CadastrarCategoriaRequest cadastrarCategoriaRequest) {
        Categoria categoria = categoriaService.inserir(cadastrarCategoriaRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id,
                                    @RequestBody @Valid AtualizarCategoriaRequest atualizarCategoriaRequest) {
        categoriaService.atualizar(id, atualizarCategoriaRequest);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}