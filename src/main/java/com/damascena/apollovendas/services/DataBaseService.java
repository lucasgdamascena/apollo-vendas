package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.*;
import com.damascena.apollovendas.domains.enums.EstadoPagamento;
import com.damascena.apollovendas.domains.enums.TipoCliente;
import com.damascena.apollovendas.repositories.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

@Service
public class DataBaseService {
    private CategoriaRepository categoriaRepository;
    private ProdutoRepository produtoRepository;
    private CidadeRepository cidadeRepository;
    private EstadoRepository estadoRepository;
    private ClienteRepository clienteRepository;
    private EnderecoRepository enderecoRepository;
    private PedidoRepository pedidoRepository;
    private PagamentoRepository pagamentoRepository;
    private ItemPedidoRepository itemPedidoRepository;

    public DataBaseService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository,
                           CidadeRepository cidadeRepository, EstadoRepository estadoRepository,
                           ClienteRepository clienteRepository, EnderecoRepository enderecoRepository,
                           PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository,
                           ItemPedidoRepository itemPedidoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public void InstanciarBaseDeTeste() throws ParseException {
        Categoria categoria1 = new Categoria("Informática");
        Categoria categoria2 = new Categoria("Escritório");
        Categoria categoria3 = new Categoria("Cama, Mesa e Banho");
        Categoria categoria4 = new Categoria("Eletrônicos");
        Categoria categoria5 = new Categoria("Jardinagem");
        Categoria categoria6 = new Categoria("Decoração");
        Categoria categoria7 = new Categoria("Perfumaria");

        Produto produto1 = new Produto("Computador", new BigDecimal("2000.00"));
        Produto produto2 = new Produto("Impressora", new BigDecimal("800.00"));
        Produto produto3 = new Produto("Mouse", new BigDecimal("80.00"));
        Produto produto4 = new Produto("Mesa de escritório", new BigDecimal("300.00"));
        Produto produto5 = new Produto("Toalha", new BigDecimal("50.00"));
        Produto produto6 = new Produto("Colcha", new BigDecimal("200.00"));
        Produto produto7 = new Produto("TV", new BigDecimal("1200.00"));
        Produto produto8 = new Produto("Roçadeira", new BigDecimal("800.00"));
        Produto produto9 = new Produto("Abajur", new BigDecimal("100.00"));
        Produto produto10 = new Produto("Condicionador", new BigDecimal("180.00"));
        Produto produto11 = new Produto("Shampoo", new BigDecimal("90.00"));

        categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
        categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
        categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
        categoria5.getProdutos().addAll(Collections.singletonList(produto8));
        categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
        categoria7.getProdutos().addAll(Collections.singletonList(produto11));

        produto1.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
        produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
        produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
        produto4.getCategorias().addAll(Collections.singletonList(categoria2));
        produto5.getCategorias().addAll(Collections.singletonList(categoria3));
        produto6.getCategorias().addAll(Collections.singletonList(categoria3));
        produto7.getCategorias().addAll(Collections.singletonList(categoria4));
        produto8.getCategorias().addAll(Collections.singletonList(categoria5));
        produto9.getCategorias().addAll(Collections.singletonList(categoria6));
        produto10.getCategorias().addAll(Collections.singletonList(categoria6));
        produto11.getCategorias().addAll(Collections.singletonList(categoria7));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2,
                categoria3, categoria4, categoria5, categoria6, categoria7));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6,
                produto7, produto8, produto9, produto10, produto11));

        Estado estado1 = new Estado("Minas Gerais");
        Estado estado2 = new Estado("São Paulo");

        Cidade cidade1 = new Cidade("Uberlândia", estado1);
        Cidade cidade2 = new Cidade("São Paulo", estado2);
        Cidade cidade3 = new Cidade("Campinas", estado2);

        estado1.getCidades().addAll(Collections.singletonList(cidade1));
        estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente("Maria Silva", "maria@gmail.com",
                "36378912377", TipoCliente.PESSOA_FISICA);
        cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838993"));

        Endereco endereco1 = new Endereco("Rua Flores",
                "300", "Apto 303", "Jardim", "38220834", cliente1, cidade1);

        Endereco endereco2 = new Endereco("Avenida Matos",
                "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);

        clienteRepository.saveAll(Collections.singletonList(cliente1));
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido pedido1 = new Pedido(cliente1, endereco1);
        Pedido pedido2 = new Pedido(cliente1, endereco2);

        Pagamento pagamento1 = new PagamentoComCartao(EstadoPagamento.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamento1);

        Pagamento pagamento2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, pedido2,
                simpleDateFormat.parse("20/10/2022 00:00"), null);
        pedido2.setPagamento(pagamento2);

        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

        ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1,
                new BigDecimal("0.00"), 1, new BigDecimal("2000.00"));
        ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3,
                new BigDecimal("0.00"), 2, new BigDecimal("80.00"));
        ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2,
                new BigDecimal("100.00"), 1, new BigDecimal("800.00"));

        pedido1.getItensPedidos().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItensPedidos().addAll(Collections.singletonList(itemPedido3));

        produto1.getItensPedidos().addAll(Collections.singletonList(itemPedido1));
        produto2.getItensPedidos().addAll(Collections.singletonList(itemPedido3));
        produto3.getItensPedidos().addAll(Collections.singletonList(itemPedido2));

        itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }
}