package com.victorxavier.product_catalog.domain.dto;

public record LoginResponse(
    String accessToken,
    Long expiresIn
) {}