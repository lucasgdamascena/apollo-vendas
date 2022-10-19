package com.damascena.apollovendas.services;

import com.damascena.apollovendas.security.UsuarioSpringSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioService {
    public static UsuarioSpringSecurity autenticado() {
        try {
            return (UsuarioSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception exception) {
            return null;
        }
    }
}