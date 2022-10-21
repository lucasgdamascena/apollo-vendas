package com.damascena.apollovendas.controllers;

import com.damascena.apollovendas.security.JWTUtil;
import com.damascena.apollovendas.security.UsuarioSpringSecurity;
import com.damascena.apollovendas.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AutorizacaoController {

    private JWTUtil jwtUtil;

    public AutorizacaoController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/refresh_token")
    public ResponseEntity refreshToken(HttpServletResponse httpServletResponse) {
        UsuarioSpringSecurity usuarioSpringSecurity = UsuarioService.autenticado();
        String token = jwtUtil.gerarToken(usuarioSpringSecurity.getUsername());
        httpServletResponse.addHeader("Authorization", "Bearer " + token);
        httpServletResponse.addHeader("access-control-expose-headers", "Authorization");

        return ResponseEntity.noContent().build();
    }
}