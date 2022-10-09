package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.dto.request.AtualizarCategoriaRequest;
import com.damascena.apollovendas.dto.request.CadastrarCategoriaRequest;
import com.damascena.apollovendas.dto.response.ListaCategoriaResponse;
import com.damascena.apollovendas.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private CategoriaService servico;

    @GetMapping
    public ResponseEntity<List<ListaCategoriaResponse>> selecionar() {
        List<ListaCategoriaResponse> listaCategoriaResponses = servico.selecionar();
        return ResponseEntity.ok().body(listaCategoriaResponses);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionarPorId(@PathVariable("id") Long id) {
        Categoria categoria = servico.selecionarPorId(id);
        return ResponseEntity.ok().body(categoria);
    }

    @GetMapping(value = "/pagina")
    public ResponseEntity<Page<ListaCategoriaResponse>> selecionarPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                                                                           @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
                                                                           @RequestParam(value = "direcao", defaultValue = "ASC") String direcao,
                                                                           @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor) {
        Page<Categoria> categoriasPaginadas = servico.selecionarPaginado(pagina, linhasPorPagina, direcao, ordenarPor);
        Page<ListaCategoriaResponse> categoriasPaginadasResponse =
                categoriasPaginadas.map(obj -> new ListaCategoriaResponse(obj));
        return ResponseEntity.ok().body(categoriasPaginadasResponse);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity inserir(@RequestBody @Valid CadastrarCategoriaRequest request) {
        Categoria categoria = servico.inserir(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarCategoriaRequest request) {
        servico.atualizar(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        servico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}