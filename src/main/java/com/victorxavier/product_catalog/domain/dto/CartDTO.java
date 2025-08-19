package com.victorxavier.product_catalog.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CartDTO(
        String id,
        String userId,
        List<CartItemDTO> items,
        BigDecimal totalAmount,
        Integer totalItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public CartDTO {
        if (items == null) {
            items = List.of();
        }
        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }
        if (totalItems == null) {
            totalItems = 0;
        }
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
}