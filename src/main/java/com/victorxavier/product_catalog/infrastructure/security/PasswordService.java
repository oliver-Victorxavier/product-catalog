package com.victorxavier.product_catalog.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom;

    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.secureRandom = new SecureRandom();
    }

    /**
     * Gera um salt aleatório
     * @return salt em formato Base64
     */
    public String generateSalt() {
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Gera o hash da senha usando BCrypt com salt personalizado
     * @param password senha em texto plano
     * @param salt salt gerado
     * @return hash da senha
     */
    public String hashPassword(String password, String salt) {
        // Combina a senha com o salt antes de fazer o hash
        String saltedPassword = password + salt;
        return passwordEncoder.encode(saltedPassword);
    }

    /**
     * Verifica se a senha fornecida corresponde ao hash armazenado
     * @param password senha em texto plano
     * @param salt salt armazenado
     * @param hash hash armazenado
     * @return true se a senha estiver correta
     */
    public boolean verifyPassword(String password, String salt, String hash) {
        String saltedPassword = password + salt;
        return passwordEncoder.matches(saltedPassword, hash);
    }

    /**
     * Classe para encapsular salt e hash
     */
    public static class SaltAndHash {
        private final String salt;
        private final String hash;

        public SaltAndHash(String salt, String hash) {
            this.salt = salt;
            this.hash = hash;
        }

        public String getSalt() {
            return salt;
        }

        public String getHash() {
            return hash;
        }
    }

    /**
     * Método conveniente para gerar salt e hash em uma única operação
     * @param password senha em texto plano
     * @return objeto contendo salt e hash
     */
    public SaltAndHash createSaltAndHash(String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        return new SaltAndHash(salt, hash);
    }
}