package com.victorxavier.product_catalog.domain.dto;

public record LoginResponse(
    String accessToken,
    String tokenType,
    Long expiresIn,
    String username,
    String email
) {
    public LoginResponse(String accessToken, String tokenType, Long expiresIn, String username, String email) {
        this.accessToken = accessToken;
        this.tokenType = tokenType != null ? tokenType : "Bearer";
        this.expiresIn = expiresIn;
        this.username = username;
        this.email = email;
    }
}