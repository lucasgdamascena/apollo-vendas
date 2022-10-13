package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.dto.request.AtualizarCategoriaRequest;
import com.damascena.apollovendas.dto.request.CadastrarCategoriaRequest;
import com.damascena.apollovendas.dto.response.ListaCategoriaResponse;
import com.damascena.apollovendas.services.exceptions.IntegridadeVioladaException;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;
import com.damascena.apollovendas.repositories.CategoriaRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<ListaCategoriaResponse> selecionar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<ListaCategoriaResponse> listaCategoriaResponse =
                categorias.stream().map(c -> new ListaCategoriaResponse(c)).collect(Collectors.toList());

        return listaCategoriaResponse;
    }

    public Categoria selecionarPorId(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Categoria.class));
    }

    public Page<Categoria> selecionarPaginado(Integer pagina,
                                              Integer linhasPorPagina,
                                              String direcao,
                                              String ordenarPor) {
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria inserir(CadastrarCategoriaRequest cadastrarCategoriaRequest) {
        Categoria categoria = cadastrarCategoriaRequest.toCategoria();
        categoriaRepository.save(categoria);

        return categoria;
    }

    public void atualizar(Long id, AtualizarCategoriaRequest atualizarCategoriaRequest) {
        Categoria categoriaEncontrada = selecionarPorId(id);
        categoriaEncontrada.setNome(atualizarCategoriaRequest.getNome());

        categoriaRepository.save(categoriaEncontrada);
    }

    public void deletar(Long id) {
        selecionarPorId(id);

        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IntegridadeVioladaException("Não é possível deletar uma categoria que tenha produtos.");
        }
    }
}