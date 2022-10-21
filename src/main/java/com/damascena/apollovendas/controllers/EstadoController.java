package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.dto.response.CidadeResponse;
import com.damascena.apollovendas.dto.response.EstadoResponse;
import com.damascena.apollovendas.services.CidadeService;
import com.damascena.apollovendas.services.EstadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    private EstadoService estadoService;
    private CidadeService cidadeService;

    public EstadoController(EstadoService estadoService, CidadeService cidadeService) {
        this.estadoService = estadoService;
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public ResponseEntity selecionarEstados() {
        List<EstadoResponse> estados = estadoService.selecionar();
        return ResponseEntity.ok().body(estados);
    }

    @GetMapping(value = "/{estadoId}/cidades")
    public ResponseEntity selecionarCidadesPorEstado(@PathVariable Long estadoId) {
        List<CidadeResponse> cidades = cidadeService.selecionarCidadesPorEstado(estadoId);
        return ResponseEntity.ok().body(cidades);
    }
}