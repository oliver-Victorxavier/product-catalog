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
        this.passwordEncoder = new BCryptPasswordEncoder(12, secureRandom);
    }

    @Override
    public String generateSalt() {
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    @Override
    public String hashPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return passwordEncoder.encode(saltedPassword);
    }

    @Override
    public boolean verifyPassword(String password, String salt, String hash) {
        System.out.println("=== PASSWORD VERIFICATION DEBUG ===");
        System.out.println("Password: " + password);
        System.out.println("Salt: " + salt);
        System.out.println("Hash: " + hash);
        String saltedPassword = password + salt;
        System.out.println("Salted password: " + saltedPassword);
        boolean matches = passwordEncoder.matches(saltedPassword, hash);
        System.out.println("Matches: " + matches);
        System.out.println("=== END DEBUG ===");
        return matches;
    }
}