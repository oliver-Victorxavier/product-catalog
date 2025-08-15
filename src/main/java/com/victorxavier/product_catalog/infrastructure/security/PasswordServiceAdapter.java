package com.victorxavier.product_catalog.infrastructure.security;

import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.value.SaltAndHash;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordServiceAdapter implements PasswordService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;

    public PasswordServiceAdapter() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.secureRandom = new SecureRandom();
    }

    @Override
    public String generateSalt() {
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    @Override
    public String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return passwordEncoder.encode(saltedPassword);
    }

    @Override
    public boolean verifyPassword(String password, String hashedPassword, String salt) {
        String saltedPassword = password + salt;
        return passwordEncoder.matches(saltedPassword, hashedPassword);
    }

    @Override
    public SaltAndHash createSaltAndHash(String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        return new SaltAndHash(salt, hash);
    }
}