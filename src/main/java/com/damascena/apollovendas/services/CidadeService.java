package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Cidade;
import com.damascena.apollovendas.dto.response.CidadeResponse;
import com.damascena.apollovendas.repositories.CidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService {

    private CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public List<CidadeResponse> selecionarCidadesPorEstado(Long id) {
        List<Cidade> cidades = cidadeRepository.findCidades(id);

        List<CidadeResponse> cidadeResponses
                = cidades.stream().map(cidade -> new CidadeResponse(cidade)).collect(Collectors.toList());
        return cidadeResponses;
    }
}