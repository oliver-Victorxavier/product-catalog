package com.victorxavier.product_catalog.domain.service;

import java.time.Instant;
import java.util.List;

public interface JwtService {

    String generateToken(String subject, List<String> scopes, Instant issuedAt, Instant expiresAt);
}