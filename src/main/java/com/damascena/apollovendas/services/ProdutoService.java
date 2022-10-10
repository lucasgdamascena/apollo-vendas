package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.domains.Produto;
import com.damascena.apollovendas.repositories.CategoriaRepository;
import com.damascena.apollovendas.repositories.ProdutoRepository;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto selecionarPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Produto.class));
    }

    public Page<Produto> selecionarPorNome(String nome,
                                           List<Long> categoriaIds,
                                           Integer pagina,
                                           Integer linhasPorPagina,
                                           String direcao,
                                           String ordenarPor) {

        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);
        List<Categoria> categorias = categoriaRepository.findAllById(categoriaIds);

        return produtoRepository.selecionarPorNome(nome, categorias, pageRequest);
    }
}