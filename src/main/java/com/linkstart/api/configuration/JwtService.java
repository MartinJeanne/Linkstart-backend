package com.linkstart.api.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final long JWT_EXPIRATION = 1000 * 60 * 60; // = 1 hour

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        String clientName = userDetails.getUsername();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        // Generate the token, also named a signed JWT (JWS)
        return Jwts.builder()
                .subject(clientName)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    private Jws<Claims> parseJwt(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT expired or incorrect");
        }
    }

    public String extractClientName(String token) {
        return parseJwt(token)
                .getPayload()
                .getSubject();
    }
}
