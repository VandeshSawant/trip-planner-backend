package com.vandesh.tripplanner.trip_planner_api.auth;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  // Secret key used to sign tokens — keep this private, never commit it
  // We'll move this to application.properties later
  @Value("${jwt.secret}")
  private String SECRET_KEY;

  // Generates a token for a given userId
  public String generateToken(Long userId) {
    return Jwts.builder()
        .setSubject(String.valueOf(userId)) // store userId inside the token
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  // Reads a token and extracts the userId from it
  public Long extractUserId(String token) {
    String subject = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    return Long.parseLong(subject);
  }

  // Checks if a token is valid (not expired, not tampered with)
  public boolean isTokenValid(String token) {
    try {
      extractUserId(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }
}