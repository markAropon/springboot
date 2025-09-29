package com.bootcamp.quickdemo.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

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
    private String jwtSecret;
    private long jwtExpirationMilliseconds;

    public String generateToken(org.springframework.security.core.Authentication authentication){
        String userName = authentication.getName();
        Date currentdate = new Date();
          Date epirationdate = new Date(currentdate.getTime() + jwtExpirationMilliseconds);

          return Jwts.builder().subject(userName).issuedAt(currentdate).expiration(epirationdate).signWith(getSignInKey()).compact();
    }
    private SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    };

    public String getUsername(String token){
        Claims claims = Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
    public boolean isValidToken(String token){
        try{
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token);
            return true;
        }catch(IllegalArgumentException | JwtException e){
            return false;
        }
    }
}