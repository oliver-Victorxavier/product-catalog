package com.victorxavier.product_catalog.infrastructure.security;

import com.victorxavier.product_catalog.domain.service.JwtService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class JwtServiceAdapter implements JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtServiceAdapter(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public String generateToken(String subject, List<String> scopes, Instant issuedAt, Instant expiresAt) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(subject)
                .claim("scope", String.join(" ", scopes))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}