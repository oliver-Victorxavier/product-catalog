package com.victorxavier.product_catalog.infrastructure.web.controller;

import com.victorxavier.product_catalog.domain.dto.AddToCartRequest;
import com.victorxavier.product_catalog.domain.dto.CartDTO;
import com.victorxavier.product_catalog.domain.dto.UpdateCartItemRequest;
import com.victorxavier.product_catalog.domain.usecase.AddToCartUseCase;
import com.victorxavier.product_catalog.domain.usecase.ClearCartUseCase;
import com.victorxavier.product_catalog.domain.usecase.GetCartUseCase;
import com.victorxavier.product_catalog.domain.usecase.RemoveFromCartUseCase;
import com.victorxavier.product_catalog.domain.usecase.UpdateCartItemUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final AddToCartUseCase addToCartUseCase;
    private final GetCartUseCase getCartUseCase;
    private final UpdateCartItemUseCase updateCartItemUseCase;
    private final RemoveFromCartUseCase removeFromCartUseCase;
    private final ClearCartUseCase clearCartUseCase;

    public CartController(AddToCartUseCase addToCartUseCase,
                         GetCartUseCase getCartUseCase,
                         UpdateCartItemUseCase updateCartItemUseCase,
                         RemoveFromCartUseCase removeFromCartUseCase,
                         ClearCartUseCase clearCartUseCase) {
        this.addToCartUseCase = addToCartUseCase;
        this.getCartUseCase = getCartUseCase;
        this.updateCartItemUseCase = updateCartItemUseCase;
        this.removeFromCartUseCase = removeFromCartUseCase;
        this.clearCartUseCase = clearCartUseCase;
    }

    /**
     * Busca o carrinho do usuário autenticado
     * Usuários podem acessar apenas seu próprio carrinho
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CartDTO> getCart() {
        String currentUserId = getCurrentUserId();
        
        Optional<CartDTO> cart = getCartUseCase.getCartByUserId(currentUserId);
        return cart.map(cartDTO -> ResponseEntity.ok(cartDTO))
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Adiciona um item ao carrinho do usuário autenticado
     * Usuários podem adicionar apenas ao seu próprio carrinho
     */
    @PostMapping("/items")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CartDTO> addToCart(@Valid @RequestBody AddToCartRequest request) {
        String currentUserId = getCurrentUserId();
        
        CartDTO cart = addToCartUseCase.addToCart(
                currentUserId, 
                request.productId(), 
                request.quantity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    /**
     * Atualiza a quantidade de um item no carrinho
     */
    @PutMapping("/items")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CartDTO> updateCartItem(@Valid @RequestBody UpdateCartItemRequest request) {
        String currentUserId = getCurrentUserId();
        
        CartDTO cart = updateCartItemUseCase.updateCartItem(
                currentUserId, 
                request.productId(), 
                request.quantity()
        );
        return ResponseEntity.ok(cart);
    }

    /**
     * Remove um item do carrinho
     */
    @DeleteMapping("/items/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CartDTO> removeFromCart(@PathVariable Long productId) {
        String currentUserId = getCurrentUserId();
        
        CartDTO cart = removeFromCartUseCase.removeFromCart(currentUserId, productId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Limpa todos os itens do carrinho
     */
    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> clearCart() {
        String currentUserId = getCurrentUserId();
        
        clearCartUseCase.clearCart(currentUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtém o ID do usuário autenticado
     */
    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}