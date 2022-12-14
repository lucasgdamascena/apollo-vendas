package com.damascena.apollovendas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String segredo;

    @Value("${jwt.expiration}")
    private Long expiracao;

    public String gerarToken(String usuario) {
        return Jwts.builder()
                .setSubject(usuario)
                .setExpiration(new Date(System.currentTimeMillis() + expiracao))
                .signWith(SignatureAlgorithm.HS512, segredo.getBytes())
                .compact();
    }

    //Reivindicações (Usuário e Tempo de Expiração)
    public boolean tokenValido(String token) {
        //Reivindicações
        Claims claims = getClaims(token);

        if (claims != null) {
            String usuario = claims.getSubject();
            Date dataExpiracao = claims.getExpiration();
            Date agora = new Date(System.currentTimeMillis());

            if (usuario != null && dataExpiracao != null && agora.before(dataExpiracao)) {
                return true;
            }
        }

        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(segredo.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception exception) {
            return null;
        }
    }

    public String getNomeUsuario(String token) {
        Claims claims = getClaims(token);

        if (claims != null) {
            return claims.getSubject();
        }

        return null;
    }
}