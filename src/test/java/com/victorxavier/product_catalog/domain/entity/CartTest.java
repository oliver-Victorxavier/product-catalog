package com.victorxavier.product_catalog.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cart - Testes Unitários")
class CartTest {

    private Cart cart;
    private final String userId = "user123";

    @BeforeEach
    void setUp() {
        cart = new Cart(userId);
    }

    @Test
    @DisplayName("Deve criar carrinho com userId válido")
    void shouldCreateCartWithValidUserId() {
        // When
        Cart newCart = new Cart(userId);

        // Then
        assertNotNull(newCart);
        assertEquals(userId, newCart.getUserId());
        assertNotNull(newCart.getId());
        assertNotNull(newCart.getItems());
        assertTrue(newCart.getItems().isEmpty());
        assertNotNull(newCart.getCreatedAt());
        assertNotNull(newCart.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar carrinho com userId inválido")
    void shouldThrowExceptionWhenCreatingCartWithInvalidUserId() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> new Cart(null));
        assertThrows(IllegalArgumentException.class, () -> new Cart(""));
        assertThrows(IllegalArgumentException.class, () -> new Cart("   "));
    }

    @Test
    @DisplayName("Deve adicionar item ao carrinho")
    void shouldAddItemToCart() {
        // Given
        CartItem item = new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 2, null);

        // When
        cart.addItem(item);

        // Then
        assertEquals(1, cart.getItems().size());
        assertTrue(cart.getItems().contains(item));
    }

    @Test
    @DisplayName("Deve atualizar quantidade quando adicionar item existente")
    void shouldUpdateQuantityWhenAddingExistingItem() {
        // Given
        CartItem item1 = new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 2, null);
        CartItem item2 = new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 3, null);
        
        cart.addItem(item1);

        // When
        cart.addItem(item2);

        // Then
        assertEquals(1, cart.getItems().size());
        CartItem updatedItem = cart.getItems().get(0);
        assertEquals(Integer.valueOf(5), updatedItem.getQuantity());
    }

    @Test
    @DisplayName("Deve remover item do carrinho")
    void shouldRemoveItemFromCart() {
        // Given
        CartItem item = new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 2, null);
        cart.addItem(item);

        // When
        boolean removed = cart.removeItem(1L);

        // Then
        assertTrue(removed);
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    @DisplayName("Deve retornar false ao tentar remover item inexistente")
    void shouldReturnFalseWhenRemovingNonExistentItem() {
        // When
        boolean removed = cart.removeItem(999L);

        // Then
        assertFalse(removed);
    }

    @Test
    @DisplayName("Deve atualizar quantidade de item existente")
    void shouldUpdateQuantityOfExistingItem() {
        // Given
        CartItem item = new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 2, null);
        cart.addItem(item);

        // When
        boolean updated = cart.updateItemQuantity(1L, 5);

        // Then
        assertTrue(updated);
        assertEquals(Integer.valueOf(5), cart.getItems().get(0).getQuantity());
    }

    @Test
    @DisplayName("Deve retornar false ao tentar atualizar item inexistente")
    void shouldReturnFalseWhenUpdatingNonExistentItem() {
        // When
        boolean updated = cart.updateItemQuantity(999L, 5);

        // Then
        assertFalse(updated);
    }

    @Test
    @DisplayName("Deve limpar todos os itens do carrinho")
    void shouldClearAllItemsFromCart() {
        // Given
        cart.addItem(new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 2, null));
        cart.addItem(new CartItem(2L, "Produto 2", new BigDecimal("20.00"), 1, null));

        // When
        cart.clearItems();

        // Then
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    @DisplayName("Deve calcular valor total corretamente")
    void shouldCalculateTotalAmountCorrectly() {
        // Given
        cart.addItem(new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 2, null)); // 20.00
        cart.addItem(new CartItem(2L, "Produto 2", new BigDecimal("15.50"), 3, null)); // 46.50

        // When
        BigDecimal totalAmount = cart.getTotalAmount();

        // Then
        assertEquals(new BigDecimal("66.50"), totalAmount);
    }

    @Test
    @DisplayName("Deve retornar zero como valor total para carrinho vazio")
    void shouldReturnZeroTotalAmountForEmptyCart() {
        // When
        BigDecimal totalAmount = cart.getTotalAmount();

        // Then
        assertEquals(BigDecimal.ZERO, totalAmount);
    }

    @Test
    @DisplayName("Deve calcular total de itens corretamente")
    void shouldCalculateTotalItemsCorrectly() {
        // Given
        cart.addItem(new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 2, null));
        cart.addItem(new CartItem(2L, "Produto 2", new BigDecimal("15.50"), 3, null));

        // When
        Integer totalItems = cart.getTotalItems();

        // Then
        assertEquals(Integer.valueOf(5), totalItems);
    }

    @Test
    @DisplayName("Deve retornar zero como total de itens para carrinho vazio")
    void shouldReturnZeroTotalItemsForEmptyCart() {
        // When
        Integer totalItems = cart.getTotalItems();

        // Then
        assertEquals(Integer.valueOf(0), totalItems);
    }

    @Test
    @DisplayName("Deve verificar se carrinho está vazio")
    void shouldCheckIfCartIsEmpty() {
        // When & Then
        assertTrue(cart.isEmpty());

        // Given
        cart.addItem(new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 1, null));

        // When & Then
        assertFalse(cart.isEmpty());
    }

    @Test
    @DisplayName("Deve atualizar timestamp ao modificar carrinho")
    void shouldUpdateTimestampWhenModifyingCart() {
        // Given
        LocalDateTime initialUpdatedAt = cart.getUpdatedAt();

        // Aguarda um pouco para garantir diferença no timestamp
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // When
        cart.updateTimestamp();

        // Then
        assertTrue(cart.getUpdatedAt().isAfter(initialUpdatedAt));
    }

    @Test
    @DisplayName("Deve buscar item por productId")
    void shouldFindItemByProductId() {
        // Given
        CartItem item = new CartItem(1L, "Produto 1", new BigDecimal("10.00"), 2, null);
        cart.addItem(item);

        // When
        CartItem foundItem = cart.findItemByProductId(1L);

        // Then
        assertNotNull(foundItem);
        assertEquals(item.getProductId(), foundItem.getProductId());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar item inexistente")
    void shouldReturnNullWhenFindingNonExistentItem() {
        // When
        CartItem foundItem = cart.findItemByProductId(999L);

        // Then
        assertNull(foundItem);
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar item nulo")
    void shouldThrowExceptionWhenAddingNullItem() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(null));
    }
}