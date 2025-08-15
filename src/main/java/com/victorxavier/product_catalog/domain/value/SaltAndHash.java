package com.victorxavier.product_catalog.domain.value;

public class SaltAndHash {

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