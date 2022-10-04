package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.dto.request.CategoriaRequest;
import com.damascena.apollovendas.dto.response.ListaCategoriaResponse;
import com.damascena.apollovendas.services.exceptions.IntegridadeVioladaException;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;
import com.damascena.apollovendas.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CategoriaRepository repositorio;

    public Categoria selecionarPorId(Long id) {
        Optional<Categoria> categoria = repositorio.findById(id);
        return categoria.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Categoria.class));
    }

    public Categoria inserir(CategoriaRequest request) {
        Categoria categoria = request.toCategoria();
        repositorio.save(categoria);

        return categoria;
    }

    public void atualizar(Long id, CategoriaRequest request) {
        Categoria categoriaEncontrada = selecionarPorId(id);
        categoriaEncontrada.setNome(request.getNome());

        repositorio.save(categoriaEncontrada);
    }

    public void deletar(Long id) {
        selecionarPorId(id);

        try {
            repositorio.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IntegridadeVioladaException("Não é possível deletar uma categoria que tenha produtos.");
        }
    }

    public List<ListaCategoriaResponse> selecionarTodos() {
        List<Categoria> categorias = repositorio.findAll();
        List<ListaCategoriaResponse> listaCategoriaResponse =
                categorias.stream().map(c -> new ListaCategoriaResponse(c)).collect(Collectors.toList());

        return listaCategoriaResponse;
    }

    public Page<Categoria> selecionarPaginado(Integer pagina,
                                              Integer linhasPorPagina,
                                              String direcao,
                                              String ordenarPor) {
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);
        return repositorio.findAll(pageRequest);
    }
}