package com.victorxavier.product_catalog.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartItemRedisEntity {
    
    private Long productId;
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;
    private String productImageUrl;
    private LocalDateTime addedAt;

    public CartItemRedisEntity() {}

    public CartItemRedisEntity(Long productId, String productName, BigDecimal unitPrice, 
                              Integer quantity, String productImageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.productImageUrl = productImageUrl;
        this.addedAt = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    // Métodos de negócio
    public BigDecimal getSubtotal() {
        if (unitPrice == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void updatePrice(BigDecimal newPrice) {
        this.unitPrice = newPrice;
    }

    public void updateQuantity(Integer newQuantity) {
        if (newQuantity == null || newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = newQuantity;
    }
}