package com.victorxavier.product_catalog.infrastructure.util;

import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class KeyGenerator {
    
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        // Save private key
        try (FileWriter privateKeyWriter = new FileWriter("src/main/resources/app.key")) {
            privateKeyWriter.write("-----BEGIN PRIVATE KEY-----\n");
            privateKeyWriter.write(Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(privateKey.getEncoded()));
            privateKeyWriter.write("\n-----END PRIVATE KEY-----\n");
        }
        
        // Save public key
        try (FileWriter publicKeyWriter = new FileWriter("src/main/resources/app.pub")) {
            publicKeyWriter.write("-----BEGIN PUBLIC KEY-----\n");
            publicKeyWriter.write(Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(publicKey.getEncoded()));
            publicKeyWriter.write("\n-----END PUBLIC KEY-----\n");
        }
        
        System.out.println("Keys generated successfully!");
    }
}