package com.example.DataSample.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String secretKey = "vHFdod6HvJvhxTej3XgOp2w0my1iXr6loS8RCDotykbfYF4TBlF/B/4d4aLlKfTzmKIvW/iAfopZGV3QkWhuEUjlraGchj+klK4KDFgYtG15OaaLKivcQMSZCI3WfZatLhncGWbY+ZiYyqwBHGKe9mQH3S35Oqouq7KR4fp1M4zMA5ntC5Z1niyYObBYFpyZ98kyLfO+l9R8SvI4thxf6ZqvEZ7STBHt/N2/ctHg/ugsOUQtTLrbeS5zjEpc8MD3WiBlAimaVBSUUzRiPDRhL9k/YLhCG1Xy443AAWZILuPD/BkiNwyl5nouMaQj2oJ1j/Ghsy6QTBgPv+YdgfJVRWnLjUPvwPKYrF32eIK6S2c=";

    public String generateToken(UserDetailsImpl userDetailsImpl) {
        return generateToken(new HashMap<>(), userDetailsImpl);
    }
    
    public String generateToken(Map<String, Object> extractClaims, UserDetailsImpl userDetailsImpl) {
        return Jwts.builder()
            .setClaims(extractClaims)
            .setSubject(userDetailsImpl.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /*
     * 
    public String extractEmail(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build();

        return jwtParser.parseClaimsJws(token)
        .getBody()
        .getSubject();
    }
     */

    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }


    public boolean isTokenValid(String token, UserDetailsImpl userDetailsImpl) {
        final String username = extractUsername(token);
        return (username.equals(userDetailsImpl.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }




}
