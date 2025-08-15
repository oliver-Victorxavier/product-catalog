package com.victorxavier.product_catalog.domain.service;

import com.victorxavier.product_catalog.domain.value.SaltAndHash;

public interface PasswordService {

    String generateSalt();

    String hashPassword(String password, String salt);

    boolean verifyPassword(String password, String hashedPassword, String salt);

    SaltAndHash createSaltAndHash(String password);
}