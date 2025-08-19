package com.victorxavier.product_catalog.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CartItemDTO(
        Long productId,
        String productName,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal subtotal,
        String productImageUrl,
        LocalDateTime addedAt
) {
    public CartItemDTO {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price cannot be null or negative");
        }
        if (subtotal == null) {
            subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
}