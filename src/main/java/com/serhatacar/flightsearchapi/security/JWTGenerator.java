package com.serhatacar.flightsearchapi.security;

import io.jsonwebtoken.*;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * JWT generator and validator using HMAC SHA-512 signature and constant secret of the API
 */
@Component
public class JWTGenerator {

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Instant currentInstant =  Instant.now();
        Instant expirationInstant = currentInstant.plus(SecurityConstants.JWT_EXPIRATION, ChronoUnit.MILLIS);

        Date issueDate = Date.from(currentInstant);
        Date expirationDate = Date.from(expirationInstant);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();

        System.out.println("New token: " + token);
        System.out.println("Issue date: " + issueDate);
        System.out.println("Expiration date: " + expirationDate);

        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        }
        catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect.");
        }
    }

}