package com.ming.shopdemo.auth.service.impl;

import com.ming.shopdemo.auth.exception.UnAuthorizedException;
import com.ming.shopdemo.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiration-seconds}")
    private long tokenExpiration;

    @Value("${jwt.secret-key}")
    private String secretKey;


    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(
                Map.of("role", userDetails.getAuthorities()),
                userDetails.getUsername()
        );
    }

    @Override
    public String generateToken(Map<String, Object> claims, String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .signWith(getSecretKey())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(tokenExpiration)))
                .compact();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    @Override
    public String getSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject().equals(userDetails.getUsername()) &&
                !isTokenExpired(claims.getExpiration());
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        boolean isBearerToken = bearerToken != null && bearerToken.startsWith("Bearer ");
        return isBearerToken ? bearerToken.substring(7) : null;
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new UnAuthorizedException("Invalid JWT token: " + e.getMessage());
        }

    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private boolean isTokenExpired(Date expiration) {
        return expiration.before(Date.from(Instant.now()));
    }
}
