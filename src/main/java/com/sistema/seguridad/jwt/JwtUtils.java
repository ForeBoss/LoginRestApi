package com.sistema.seguridad.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    private String secretKey = "c5de91fca652dcf22bc12c882d0b3711515f666b33ce9ef2b1a9ce3c2c341166";
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    private static final long DEFAULT_EXPIRATION_TIME = 3600000;

    public String generateAccesToken(String username){
        long expirationTime = (timeExpiration != null) ? Long.parseLong(timeExpiration) : DEFAULT_EXPIRATION_TIME;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error("Token invalido, error: ".concat(e.getMessage()));
            return false;
        }
    }


    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClains(token);
        return claimsTFunction.apply(claims);
    }

    public Claims extractAllClains(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignatureKey() {
        if (secretKey == null || secretKey.isEmpty()) {
            log.error("La clave secreta (secretKey) es nula o vacía.");
            // Aquí puedes manejar la situación de clave secreta nula o vacía según tus requisitos.
            // Puedes lanzar una excepción, devolver null o tomar alguna otra acción apropiada.
            // Por ejemplo, puedes lanzar una excepción:
            throw new IllegalArgumentException("La clave secreta (secretKey) es nula o vacía.");
        }

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
