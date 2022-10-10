package com.damascena.apollovendas.repositories;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.domains.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT DISTINCT produto FROM Produto produto INNER JOIN produto.categorias cat WHERE cat.nome LIKE %:nome% AND cat IN :categorias")
    Page<Produto> selecionarPorNome(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

    //A linha abaixo também poderia ser utilizada como alternativa.
    //Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}