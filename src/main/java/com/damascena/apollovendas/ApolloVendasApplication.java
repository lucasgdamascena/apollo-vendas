package com.damascena.apollovendas;

import com.damascena.apollovendas.domains.*;
import com.damascena.apollovendas.domains.enums.TipoCliente;
import com.damascena.apollovendas.repositories.*;
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

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

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

        Estado estado1 = new Estado(null, "Minas Gerais");
        Estado estado2 = new Estado(null, "São Paulo");

        Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
        Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
        Cidade cidade3 = new Cidade(null, "Campinas", estado2);

        estado1.getCidades().addAll(Arrays.asList(cidade1));
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com",
                "36378912377", TipoCliente.PESSOA_FISICA);
        cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838993"));

        Endereco endereco1 = new Endereco(null, "Rua Flores",
                "300", "Apto 303", "Jardim", "38220834", cliente1, cidade1);

        Endereco endereco2 = new Endereco(null, "Avenida Matos",
                "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);

        clienteRepository.saveAll(Arrays.asList(cliente1));
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
    }
}