package com.angerasilas.petroflow_backend.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour expiry
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false; // Expired token
        } catch (Exception e) {
            return false; // Invalid token
        }
    }

    // Extract Username
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Check Expiration
    public boolean isTokenExpired(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                    .parseClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // Expired
        }
    }

    // Refresh Token
    public String refreshToken(String token) {
        if (isTokenExpired(token)) {
            String username = extractUsername(token);
            return generateToken(username);
        }
        return token; // Token still valid, return same token
    }
}