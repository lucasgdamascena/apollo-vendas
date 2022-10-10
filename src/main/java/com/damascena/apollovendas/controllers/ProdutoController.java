package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.controllers.utils.URL;
import com.damascena.apollovendas.domains.Produto;
import com.damascena.apollovendas.dto.response.ListaProdutoResponse;
import com.damascena.apollovendas.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity selecionarPorId(@PathVariable("id") Long id) {
        Produto produto = produtoService.selecionarPorId(id);
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping
    public ResponseEntity<Page<ListaProdutoResponse>> selecionarPaginado(@RequestParam(value = "nome", defaultValue = "") String nome,
                                                                         @RequestParam(value = "categorias", defaultValue = "") String categorias,
                                                                         @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                                                                         @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
                                                                         @RequestParam(value = "direcao", defaultValue = "ASC") String direcao,
                                                                         @RequestParam(value = "ordenarPor", defaultValue = "nome") String ordenarPor) {

        List<Long> categoriaIds = URL.decodificarLongList(categorias);

        Page<Produto> produtosPaginados
                = produtoService.selecionarPorNome(URL.decodificarParam(nome), categoriaIds, pagina, linhasPorPagina, direcao, ordenarPor);

        Page<ListaProdutoResponse> produtosPaginadosResponse
                = produtosPaginados.map(obj -> new ListaProdutoResponse(obj));

        return ResponseEntity.ok().body(produtosPaginadosResponse);
    }
}