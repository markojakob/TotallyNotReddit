package com.example.backend.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET = "uE9DugDKYCO4xKZjWsu0gwo+HaDTfyqviW862sqVr0I=";

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }


    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // returns the username stored in the token
    }

    // Check if token belongs to the username
    public boolean isValid(String token, String username) {
        return extractUsername(token).equals(username);
    }
}