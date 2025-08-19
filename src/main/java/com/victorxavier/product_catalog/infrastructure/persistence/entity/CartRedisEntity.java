package com.victorxavier.product_catalog.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RedisHash(value = "cart", timeToLive = 86400) // TTL de 24 horas
public class CartRedisEntity {
    
    @Id
    private String id;
    
    @Indexed
    private String userId;
    
    private List<CartItemRedisEntity> items = new ArrayList<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CartRedisEntity() {}

    public CartRedisEntity(String userId) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItemRedisEntity> getItems() {
        return items;
    }

    public void setItems(List<CartItemRedisEntity> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Métodos de negócio
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(CartItemRedisEntity::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Integer getTotalItems() {
        return items.stream()
                .mapToInt(CartItemRedisEntity::getQuantity)
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}