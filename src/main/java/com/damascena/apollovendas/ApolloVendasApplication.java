package com.damascena.apollovendas;

import com.damascena.apollovendas.domains.Categoria;
import com.damascena.apollovendas.domains.Produto;
import com.damascena.apollovendas.repositories.CategoriaRepository;
import com.damascena.apollovendas.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class ApolloVendasApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApolloVendasApplication.class, args);
    }

    //OPERAÇÃO DE INSTANCIAÇÃO
    @Override
    public void run(String... args) throws Exception {
        Categoria categoria1 = new Categoria(null, "Informática");
        Categoria categoria2 = new Categoria(null, "Escritório");

        Produto produto1 = new Produto(null, "Computador", new BigDecimal("2000.00"));
        Produto produto2 = new Produto(null, "Impressora", new BigDecimal("800.00"));
        Produto produto3 = new Produto(null, "Mouse", new BigDecimal("80.00"));

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto2));

        produto1.getCategorias().addAll(Arrays.asList(categoria1));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
        produto3.getCategorias().addAll(Arrays.asList(categoria1));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
    }
}