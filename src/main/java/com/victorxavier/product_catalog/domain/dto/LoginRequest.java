package com.victorxavier.product_catalog.domain.dto;

public record LoginRequest(
    String username,
    String password
) {}