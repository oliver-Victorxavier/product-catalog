package com.victorxavier.product_catalog.application.usecase.impl.cart;

import com.victorxavier.product_catalog.domain.dto.CartDTO;
import com.victorxavier.product_catalog.domain.entity.Cart;
import com.victorxavier.product_catalog.domain.entity.CartItem;
import com.victorxavier.product_catalog.domain.entity.Product;
import com.victorxavier.product_catalog.domain.repository.CartRepository;
import com.victorxavier.product_catalog.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CartServiceImpl - Testes Unitários")
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private final String userId = "user123";
    private final Long productId = 1L;
    private Product product;
    private Cart cart;

    @BeforeEach
    void setUp() {
        product = new Product(
                productId,
                "Produto Teste",
                "Descrição do produto",
                new BigDecimal("29.99"),
                "http://example.com/image.jpg",
                java.time.Instant.now()
        );

        cart = new Cart(userId);
    }

    @Test
    @DisplayName("Deve adicionar item ao carrinho existente")
    void shouldAddItemToExistingCart() {
        // Given
        Integer quantity = 2;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // When
        CartDTO result = cartService.addToCart(userId, productId, quantity);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.userId());
        assertEquals(1, result.items().size());
        assertEquals(productId, result.items().get(0).productId());
        assertEquals(quantity, result.items().get(0).quantity());

        verify(productRepository).findById(productId);
        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Deve criar novo carrinho quando não existir")
    void shouldCreateNewCartWhenNotExists() {
        // Given
        Integer quantity = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // When
        CartDTO result = cartService.addToCart(userId, productId, quantity);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.userId());

        verify(productRepository).findById(productId);
        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não existir")
    void shouldThrowExceptionWhenProductNotExists() {
        // Given
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> cartService.addToCart(userId, productId, 1));
        
        assertEquals("Produto não encontrado", exception.getMessage());
        verify(productRepository).findById(productId);
        verifyNoInteractions(cartRepository);
    }

    @Test
    @DisplayName("Deve atualizar quantidade quando produto já existe no carrinho")
    void shouldUpdateQuantityWhenProductAlreadyInCart() {
        // Given
        Integer initialQuantity = 2;
        Integer additionalQuantity = 3;
        
        CartItem existingItem = new CartItem(productId, product.getName(), 
                product.getPrice(), initialQuantity, product.getImgUrl());
        cart.addItem(existingItem);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // When
        CartDTO result = cartService.addToCart(userId, productId, additionalQuantity);

        // Then
        assertNotNull(result);
        assertEquals(1, result.items().size());
        assertEquals(Integer.valueOf(initialQuantity + additionalQuantity), 
                result.items().get(0).quantity());

        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Deve buscar carrinho por userId")
    void shouldGetCartByUserId() {
        // Given
        CartItem item = new CartItem(productId, "Produto", new BigDecimal("10.00"), 1, null);
        cart.addItem(item);
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        // When
        Optional<CartDTO> result = cartService.getCartByUserId(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().userId());
        assertEquals(1, result.get().items().size());

        verify(cartRepository).findByUserId(userId);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando carrinho não existir")
    void shouldReturnEmptyOptionalWhenCartNotExists() {
        // Given
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // When
        Optional<CartDTO> result = cartService.getCartByUserId(userId);

        // Then
        assertFalse(result.isPresent());
        verify(cartRepository).findByUserId(userId);
    }

    @Test
    @DisplayName("Deve atualizar quantidade de item no carrinho")
    void shouldUpdateCartItemQuantity() {
        // Given
        Integer newQuantity = 5;
        CartItem item = new CartItem(productId, "Produto", new BigDecimal("10.00"), 2, null);
        cart.addItem(item);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // When
        CartDTO result = cartService.updateCartItem(userId, productId, newQuantity);

        // Then
        assertNotNull(result);
        assertEquals(newQuantity, result.items().get(0).quantity());

        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar item inexistente")
    void shouldThrowExceptionWhenUpdatingNonExistentItem() {
        // Given
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> cartService.updateCartItem(userId, productId, 5));
        
        assertEquals("Item não encontrado no carrinho", exception.getMessage());
        verify(cartRepository).findByUserId(userId);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    @DisplayName("Deve remover item do carrinho")
    void shouldRemoveItemFromCart() {
        // Given
        CartItem item = new CartItem(productId, "Produto", new BigDecimal("10.00"), 2, null);
        cart.addItem(item);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // When
        CartDTO result = cartService.removeFromCart(userId, productId);

        // Then
        assertNotNull(result);
        assertTrue(result.items().isEmpty());

        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover item inexistente")
    void shouldThrowExceptionWhenRemovingNonExistentItem() {
        // Given
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> cartService.removeFromCart(userId, productId));
        
        assertEquals("Item não encontrado no carrinho", exception.getMessage());
        verify(cartRepository).findByUserId(userId);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    @DisplayName("Deve limpar carrinho")
    void shouldClearCart() {
        // Given
        CartItem item = new CartItem(productId, "Produto", new BigDecimal("10.00"), 2, null);
        cart.addItem(item);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        // When
        cartService.clearCart(userId);

        // Then
        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).deleteByUserId(userId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando carrinho não existir para operações")
    void shouldThrowExceptionWhenCartNotExistsForOperations() {
        // Given
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, 
            () -> cartService.updateCartItem(userId, productId, 5));
        
        assertThrows(RuntimeException.class, 
            () -> cartService.removeFromCart(userId, productId));
        
        assertThrows(RuntimeException.class, 
            () -> cartService.clearCart(userId));
    }

    @Test
    @DisplayName("Deve validar parâmetros de entrada")
    void shouldValidateInputParameters() {
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> cartService.addToCart(null, productId, 1));
        
        assertThrows(IllegalArgumentException.class, 
            () -> cartService.addToCart("", productId, 1));
        
        assertThrows(IllegalArgumentException.class, 
            () -> cartService.addToCart(userId, null, 1));
        
        assertThrows(IllegalArgumentException.class, 
            () -> cartService.addToCart(userId, productId, null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> cartService.addToCart(userId, productId, 0));
    }
}