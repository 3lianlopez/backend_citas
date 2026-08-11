package com.software.citas.security;


import com.software.citas.auth.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey clave;
    private final long expiracionMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms:3600000}") long expiracionMs
    ) {
        this.clave = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expiracionMs = expiracionMs;
    }

    public String generarToken(Usuario usuario) {
        Instant ahora = Instant.now();

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("usuarioId", usuario.getId())
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(ahora.plusMillis(expiracionMs)))
                .signWith(clave)
                .compact();
    }

    public String extraerEmail(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean esValido(String token) {
        try {
            Claims claims = extraerClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception exception) {
            return false;
        }
    }

    public long getExpiracionMs() {
        return expiracionMs;
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
