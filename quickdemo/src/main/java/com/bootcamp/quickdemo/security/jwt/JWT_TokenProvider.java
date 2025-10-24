package com.bootcamp.quickdemo.security.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "app")

public class JWT_TokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationMilliseconds}")
    private long jwtExpirationMilliseconds;

    public String generateToken(org.springframework.security.core.Authentication authentication) {
        String userName = authentication.getName();
        Date currentdate = new Date();
        Date epirationdate = new Date(currentdate.getTime() + jwtExpirationMilliseconds);

        return Jwts.builder().subject(userName).issuedAt(currentdate).expiration(epirationdate).signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    };

    public String getUsername(String token) {
        Claims claims = Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token);
            return true;
        } catch (IllegalArgumentException | JwtException e) {
            return false;
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateTokenFromUsername(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMilliseconds);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
}