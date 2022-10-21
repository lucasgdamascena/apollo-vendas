package com.damascena.apollovendas.services;

import com.damascena.apollovendas.domains.Estado;
import com.damascena.apollovendas.dto.response.EstadoResponse;
import com.damascena.apollovendas.repositories.EstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoService {

    private EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<EstadoResponse> selecionar() {
        List<Estado> estados = estadoRepository.findAllByOrderByNome();
        List<EstadoResponse> estadoResponses
                = estados.stream().map(estado -> new EstadoResponse(estado)).collect(Collectors.toList());
        return estadoResponses;
    }
}