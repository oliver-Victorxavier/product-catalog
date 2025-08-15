package com.victorxavier.product_catalog.infrastructure.security;

import com.victorxavier.product_catalog.domain.service.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordServiceAdapter implements PasswordService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;
    
    public PasswordServiceAdapter() {
        this.secureRandom = new SecureRandom();
        // BCrypt com strength 12 para maior segurança
        this.passwordEncoder = new BCryptPasswordEncoder(12, secureRandom);
    }

    @Override
    public String generateSalt() {
        byte[] salt = new byte[32]; // 256 bits de salt
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    @Override
    public String hashPassword(String password, String salt) {
        // BCrypt já inclui salt internamente, mas vamos usar o salt fornecido como pepper adicional
        String saltedPassword = password + salt;
        return passwordEncoder.encode(saltedPassword);
    }

    @Override
    public boolean verifyPassword(String password, String salt, String hash) {
        String saltedPassword = password + salt;
        return passwordEncoder.matches(saltedPassword, hash);
    }
}