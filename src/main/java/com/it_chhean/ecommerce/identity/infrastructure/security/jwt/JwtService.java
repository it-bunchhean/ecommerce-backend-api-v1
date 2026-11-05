package com.it_chhean.ecommerce.identity.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtService(
            @Value("${security.jwt.secret-key}") String base64Secret,
            @Value("${security.jwt.expiration-time}") long expirationMs
    ) {
        if (expirationMs <= 0) {
            throw new IllegalArgumentException("security.jwt.expiration-time must be greater than 0");
        }
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.expirationMs = expirationMs;
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(new HashMap<>(extractClaims))
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    public long getExpirationTime() {
        return expirationMs;
    }

    public String extractusernam(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    // return true only if the signature is valid, the token is not expired
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(token);
            return userDetails.getUsername().equals(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Rejected invalid Jwt: {}", e.getMessage());
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}