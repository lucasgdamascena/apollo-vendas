package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.dto.request.AtualizarCategoriaRequest;
import com.damascena.apollovendas.dto.request.CadastrarCategoriaRequest;
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
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repositorio;

    public List<ListaCategoriaResponse> selecionar() {
        List<Categoria> categorias = repositorio.findAll();
        List<ListaCategoriaResponse> listaCategoriaResponse =
                categorias.stream().map(c -> new ListaCategoriaResponse(c)).collect(Collectors.toList());

        return listaCategoriaResponse;
    }

    public Categoria selecionarPorId(Long id) {
        Optional<Categoria> categoria = repositorio.findById(id);
        return categoria.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Categoria.class));
    }

    public Page<Categoria> selecionarPaginado(Integer pagina,
                                              Integer linhasPorPagina,
                                              String direcao,
                                              String ordenarPor) {
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);
        return repositorio.findAll(pageRequest);
    }

    public Categoria inserir(@RequestBody @Valid CadastrarCategoriaRequest request) {
        Categoria categoria = request.toCategoria();
        repositorio.save(categoria);

        return categoria;
    }

    public void atualizar(Long id, AtualizarCategoriaRequest request) {
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
}