package com.damascena.apollovendas.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFiltroAutorizacao extends BasicAuthenticationFilter {

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JWTFiltroAutorizacao(AuthenticationManager authenticationManager,
                                JWTUtil jwtUtil,
                                UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");
        String token = header.substring(7);

        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(token);

            if (usernamePasswordAuthenticationToken != null) {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {

        if (jwtUtil.tokenValido(token)) {
            String nomeUsuario = jwtUtil.getNomeUsuario(token);
            UserDetails usuario = userDetailsService.loadUserByUsername(nomeUsuario);

            return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }

        return null;
    }
}