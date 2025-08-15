package com.victorxavier.product_catalog.domain.dto;

public record LoginResponse(
    String token,
    String username
) {
}