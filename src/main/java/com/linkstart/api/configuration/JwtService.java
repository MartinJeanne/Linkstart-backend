package com.linkstart.api.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final long JWT_EXPIRATION = 1000 * 60 * 15;
    private static final SecretKey JWT_SECRET_KEY = Jwts.SIG.HS256.key().build(); //TODO

    public String generateToken(UserDetails userDetails) {
        String clientName = userDetails.getUsername();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        // Generate the token, also named a signed JWT (JWS)
        return Jwts.builder()
                .subject(clientName)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(JWT_SECRET_KEY)
                .compact();
    }

    private Jws<Claims> parseJwt(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(JWT_SECRET_KEY)
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
