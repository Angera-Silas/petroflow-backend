package com.angerasilas.petroflow_backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtUtil {

    private final String jwtSecret;

    public JwtUtil(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public Optional<String> extractStringClaim(String token, String claimName) {
        if (!StringUtils.hasText(token) || !StringUtils.hasText(claimName)) {
            return Optional.empty();
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Object claimValue = claims.get(claimName);
            return Optional.ofNullable(claimValue).map(Object::toString);
        } catch (JwtException | IllegalArgumentException ex) {
            log.debug("Unable to extract JWT claim {}", claimName, ex);
            return Optional.empty();
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
