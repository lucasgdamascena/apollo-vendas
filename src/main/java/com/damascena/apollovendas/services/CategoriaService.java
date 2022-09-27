package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;
import com.damascena.apollovendas.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repositorio;

    public Categoria selecionar(Long id) {
        Optional<Categoria> categoria = repositorio.findById(id);
        return categoria.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Categoria.class));
    }
}