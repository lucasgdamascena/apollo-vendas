package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Cidade;
import com.damascena.apollovendas.domains.Cliente;
import com.damascena.apollovendas.dto.request.AtualizarClienteRequest;
import com.damascena.apollovendas.dto.request.CadastrarClienteRequest;
import com.damascena.apollovendas.repositories.CidadeRepository;
import com.damascena.apollovendas.repositories.ClienteRepository;
import com.damascena.apollovendas.repositories.EnderecoRepository;
import com.damascena.apollovendas.services.exceptions.IntegridadeVioladaException;
import com.damascena.apollovendas.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private CidadeRepository cidadeRepository;
    private EnderecoRepository enderecoRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          CidadeRepository cidadeRepository,
                          EnderecoRepository enderecoRepository) {
        this.clienteRepository = clienteRepository;
        this.cidadeRepository = cidadeRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Cliente selecionarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() ->
                new ObjetoNaoEncontradoException("Objeto não encontrado. Id: " + id + ", Tipo: " + Cliente.class));
    }

    public Cliente inserir(@RequestBody @Valid CadastrarClienteRequest request) {
        Optional<Cidade> cidade = cidadeRepository.findById(request.getCidadeId());
        Cliente cliente = clienteRepository.save(request.toCliente(cidade));
        enderecoRepository.save(cliente.getEnderecos().get(0));

        return cliente;
    }

    public void atualizar(Long id, AtualizarClienteRequest request) {
        Cliente clienteEncontrado = selecionarPorId(id);
        clienteEncontrado.setNome(request.getNome());
        clienteEncontrado.setEmail(request.getEmail());

        clienteRepository.save(clienteEncontrado);
    }

    public void deletar(Long id) {
        selecionarPorId(id);

        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IntegridadeVioladaException("Não é possível deletar um cliente que tenha pedidos.");
        }
    }
}