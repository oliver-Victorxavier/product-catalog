package com.victorxavier.product_catalog.domain.service;

public interface PasswordService {
    String generateSalt();
    String hashPassword(String password, String salt);
    boolean verifyPassword(String password, String salt, String hash);
}