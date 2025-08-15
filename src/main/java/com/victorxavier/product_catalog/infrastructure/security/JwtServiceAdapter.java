package com.victorxavier.product_catalog.infrastructure.security;

import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtServiceAdapter implements JwtService {

    @Value("${jwt.private.key}")
    private Resource privateKeyResource;
    
    @Value("${jwt.public.key}")
    private Resource publicKeyResource;
    
    private PrivateKey privateKey;
    private PublicKey publicKey;
    
    // Token válido por 24 horas
    private static final long TOKEN_VALIDITY = 24 * 60 * 60 * 1000;
    
    @PostConstruct
    public void init() {
        try {
            System.out.println("Iniciando carregamento das chaves RSA...");
            System.out.println("Private key resource: " + privateKeyResource);
            System.out.println("Public key resource: " + publicKeyResource);
            
            this.privateKey = loadPrivateKey();
            System.out.println("Chave privada carregada com sucesso");
            
            this.publicKey = loadPublicKey();
            System.out.println("Chave pública carregada com sucesso");
            
            System.out.println("Chaves RSA carregadas com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar chaves RSA: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar chaves RSA", e);
        }
    }
    
    private PrivateKey loadPrivateKey() throws Exception {
        try (var inputStream = privateKeyResource.getInputStream()) {
            String privateKeyContent = new String(inputStream.readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyContent);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(spec);
        }
    }
    
    private PublicKey loadPublicKey() throws Exception {
        try (var inputStream = publicKeyResource.getInputStream()) {
            String publicKeyContent = new String(inputStream.readAllBytes())
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyContent);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        }
    }

    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plus(TOKEN_VALIDITY, ChronoUnit.MILLIS);
        
        String roles = user.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .collect(Collectors.joining(","));
        
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("roles", roles)
                .claim("email", user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
    
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}