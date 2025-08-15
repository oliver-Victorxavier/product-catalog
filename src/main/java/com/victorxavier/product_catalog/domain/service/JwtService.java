package com.victorxavier.product_catalog.domain.service;

import com.victorxavier.product_catalog.domain.entity.User;

public interface JwtService {
    String generateToken(User user);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}