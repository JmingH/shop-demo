package com.ming.shopdemo.auth.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> claims, String username);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String getSubject(String token);

    boolean validateToken(String token, UserDetails userDetails);

    String resolveToken(HttpServletRequest request);
}
