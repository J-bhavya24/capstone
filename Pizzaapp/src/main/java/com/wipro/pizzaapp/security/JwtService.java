package com.wipro.pizzaapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "d2hhdGV2ZXJ5b3V3YW50dXNlZm9yc2VjdXJlandrZXlzaG91bGRiZWNvbXBsZXh0b2JlMzJiYnl0ZXM=";

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ✅ GENERATE TOKEN (UPDATED EXPIRY → 5 HOURS)
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5)) // ✅ 5 hours
                .signWith(getSigningKey())
                .compact();
    }

    // ✅ EXTRACT EMAIL
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ TOKEN VALIDATION
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ CLAIMS
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}