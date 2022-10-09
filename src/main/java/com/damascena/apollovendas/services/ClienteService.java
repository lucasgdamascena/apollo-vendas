package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Cidade;
import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.domains.Endereco;
import com.damascena.apollovendas.dto.request.AtualizarClienteRequest;
import com.damascena.apollovendas.dto.request.CadastrarClienteRequest;
import com.damascena.apollovendas.repositories.CidadeRepository;
import com.damascena.apollovendas.repositories.ClienteRepository;
import com.damascena.apollovendas.repositories.EnderecoRepository;
import com.damascena.apollovendas.services.exceptions.IntegridadeVioladaException;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepositorio;

    @Autowired
    private CidadeRepository cidadeRepositorio;

    @Autowired
    private EnderecoRepository enderecoRepositorio;

    public Cliente selecionarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        return cliente.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Cliente.class));
    }

    public Cliente inserir(@RequestBody @Valid CadastrarClienteRequest request)  {
        Optional<Cidade> cidade = cidadeRepositorio.findById(request.getCidadeId());
        Cliente cliente = clienteRepositorio.save(request.toCliente(cidade));
        enderecoRepositorio.save(cliente.getEnderecos().get(0));

        return cliente;
    }

    public void atualizar(Long id, AtualizarClienteRequest request) {
        Cliente clienteEncontrado = selecionarPorId(id);
        clienteEncontrado.setNome(request.getNome());
        clienteEncontrado.setEmail(request.getEmail());

        clienteRepositorio.save(clienteEncontrado);
    }

    public void deletar(Long id) {
        selecionarPorId(id);

        try {
            clienteRepositorio.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IntegridadeVioladaException("Não é possível deletar um cliente que tenha pedidos.");
        }
    }
}