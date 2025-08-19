package com.victorxavier.product_catalog.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CartItem - Testes Unitários")
class CartItemTest {

    @Test
    @DisplayName("Deve criar CartItem com dados válidos")
    void shouldCreateCartItemWithValidData() {
        // Given
        Long productId = 1L;
        String productName = "Produto Teste";
        BigDecimal unitPrice = new BigDecimal("29.99");
        Integer quantity = 2;
        String imageUrl = "http://example.com/image.jpg";

        // When
        CartItem cartItem = new CartItem(productId, productName, unitPrice, quantity, imageUrl);

        // Then
        assertNotNull(cartItem);
        assertEquals(productId, cartItem.getProductId());
        assertEquals(productName, cartItem.getProductName());
        assertEquals(unitPrice, cartItem.getUnitPrice());
        assertEquals(quantity, cartItem.getQuantity());
        assertEquals(imageUrl, cartItem.getProductImageUrl());
        assertNotNull(cartItem.getAddedAt());
    }

    @Test
    @DisplayName("Deve calcular subtotal corretamente")
    void shouldCalculateSubtotalCorrectly() {
        // Given
        BigDecimal unitPrice = new BigDecimal("25.50");
        Integer quantity = 3;
        CartItem cartItem = new CartItem(1L, "Produto", unitPrice, quantity, null);

        // When
        BigDecimal subtotal = cartItem.getSubtotal();

        // Then
        assertEquals(new BigDecimal("76.50"), subtotal);
    }

    @Test
    @DisplayName("Deve atualizar quantidade com valor válido")
    void shouldUpdateQuantityWithValidValue() {
        // Given
        CartItem cartItem = new CartItem(1L, "Produto", new BigDecimal("10.00"), 1, null);
        Integer newQuantity = 5;

        // When
        cartItem.updateQuantity(newQuantity);

        // Then
        assertEquals(newQuantity, cartItem.getQuantity());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar quantidade com valor inválido")
    void shouldThrowExceptionWhenUpdatingQuantityWithInvalidValue() {
        // Given
        CartItem cartItem = new CartItem(1L, "Produto", new BigDecimal("10.00"), 1, null);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> cartItem.updateQuantity(0));
        assertThrows(IllegalArgumentException.class, () -> cartItem.updateQuantity(-1));
        assertThrows(IllegalArgumentException.class, () -> cartItem.updateQuantity(null));
    }

    @Test
    @DisplayName("Deve atualizar preço com valor válido")
    void shouldUpdatePriceWithValidValue() {
        // Given
        CartItem cartItem = new CartItem(1L, "Produto", new BigDecimal("10.00"), 1, null);
        BigDecimal newPrice = new BigDecimal("15.99");

        // When
        cartItem.updatePrice(newPrice);

        // Then
        assertEquals(newPrice, cartItem.getUnitPrice());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar preço com valor inválido")
    void shouldThrowExceptionWhenUpdatingPriceWithInvalidValue() {
        // Given
        CartItem cartItem = new CartItem(1L, "Produto", new BigDecimal("10.00"), 1, null);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> cartItem.updatePrice(null));
        assertThrows(IllegalArgumentException.class, () -> cartItem.updatePrice(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> cartItem.updatePrice(new BigDecimal("-1.00")));
    }

    @Test
    @DisplayName("Deve validar dados no construtor")
    void shouldValidateDataInConstructor() {
        // Given
        BigDecimal validPrice = new BigDecimal("10.00");
        Integer validQuantity = 1;

        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> new CartItem(null, "Produto", validPrice, validQuantity, null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new CartItem(1L, null, validPrice, validQuantity, null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new CartItem(1L, "", validPrice, validQuantity, null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new CartItem(1L, "Produto", null, validQuantity, null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new CartItem(1L, "Produto", BigDecimal.ZERO, validQuantity, null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new CartItem(1L, "Produto", validPrice, null, null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> new CartItem(1L, "Produto", validPrice, 0, null));
    }

    @Test
    @DisplayName("Deve permitir URL de imagem nula")
    void shouldAllowNullImageUrl() {
        // Given & When
        CartItem cartItem = new CartItem(1L, "Produto", new BigDecimal("10.00"), 1, null);

        // Then
        assertNotNull(cartItem);
        assertNull(cartItem.getProductImageUrl());
    }

    @Test
    @DisplayName("Deve definir addedAt automaticamente")
    void shouldSetAddedAtAutomatically() {
        // Given
        LocalDateTime before = LocalDateTime.now();

        // When
        CartItem cartItem = new CartItem(1L, "Produto", new BigDecimal("10.00"), 1, null);

        // Then
        LocalDateTime after = LocalDateTime.now();
        assertNotNull(cartItem.getAddedAt());
        assertTrue(cartItem.getAddedAt().isAfter(before) || cartItem.getAddedAt().isEqual(before));
        assertTrue(cartItem.getAddedAt().isBefore(after) || cartItem.getAddedAt().isEqual(after));
    }
}