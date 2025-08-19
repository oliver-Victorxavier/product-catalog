package com.victorxavier.product_catalog.application.usecase.impl.cart;

import com.victorxavier.product_catalog.domain.dto.CartDTO;
import com.victorxavier.product_catalog.domain.entity.Cart;
import com.victorxavier.product_catalog.domain.entity.CartItem;
import com.victorxavier.product_catalog.domain.entity.Product;
import com.victorxavier.product_catalog.domain.exception.ProductNotFoundException;
import com.victorxavier.product_catalog.domain.mapper.CartMapper;
import com.victorxavier.product_catalog.domain.repository.CartRepository;
import com.victorxavier.product_catalog.domain.repository.ProductRepository;
import com.victorxavier.product_catalog.domain.usecase.AddToCartUseCase;
import com.victorxavier.product_catalog.domain.usecase.ClearCartUseCase;
import com.victorxavier.product_catalog.domain.usecase.GetCartUseCase;
import com.victorxavier.product_catalog.domain.usecase.RemoveFromCartUseCase;
import com.victorxavier.product_catalog.domain.usecase.UpdateCartItemUseCase;

import java.util.Optional;
import java.util.UUID;

public class CartServiceImpl implements 
        AddToCartUseCase, 
        GetCartUseCase, 
        UpdateCartItemUseCase, 
        RemoveFromCartUseCase, 
        ClearCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartDTO addToCart(String userId, Long productId, Integer quantity) {

        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));

        CartItem cartItem = new CartItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                quantity,
                product.getImgUrl()
        );
        
        cart.addItem(cartItem);

        Cart savedCart = cartRepository.save(cart);
        
        return CartMapper.toDTO(savedCart);
    }

    @Override
    public Optional<CartDTO> getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId)
                .map(CartMapper::toDTO);
    }

    @Override
    public CartDTO updateCartItem(String userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        boolean updated = cart.updateItemQuantity(productId, quantity);
        if (!updated) {
            throw new RuntimeException("Item not found");
        }
        
        Cart savedCart = cartRepository.save(cart);
        return CartMapper.toDTO(savedCart);
    }

    @Override
    public CartDTO removeFromCart(String userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        boolean removed = cart.removeItem(productId);
        if (!removed) {
            throw new RuntimeException("Item not found");
        }
        
        Cart savedCart = cartRepository.save(cart);
        return CartMapper.toDTO(savedCart);
    }

    @Override
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        
        cartRepository.deleteByUserId(userId);
    }

    private Cart createNewCart(String userId) {
        Cart cart = new Cart(userId);
        cart.setId(UUID.randomUUID().toString());
        return cart;
    }
}