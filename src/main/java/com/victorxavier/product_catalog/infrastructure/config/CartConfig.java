package com.victorxavier.product_catalog.infrastructure.config;

import com.victorxavier.product_catalog.application.usecase.impl.cart.CartServiceImpl;
import com.victorxavier.product_catalog.domain.repository.CartRepository;
import com.victorxavier.product_catalog.domain.repository.ProductRepository;
import com.victorxavier.product_catalog.domain.usecase.*;
import com.victorxavier.product_catalog.infrastructure.persistence.impl.CartRepositoryImpl;
import com.victorxavier.product_catalog.infrastructure.persistence.repository.CartRedisRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig {

    @Bean
    public CartRepository cartRepository(CartRedisRepository cartRedisRepository) {
        return new CartRepositoryImpl(cartRedisRepository);
    }

    @Bean
    public CartServiceImpl cartService(CartRepository cartRepository, ProductRepository productRepository) {
        return new CartServiceImpl(cartRepository, productRepository);
    }

    @Bean
    public AddToCartUseCase addToCartUseCase(CartServiceImpl cartService) {
        return cartService;
    }

    @Bean
    public GetCartUseCase getCartUseCase(CartServiceImpl cartService) {
        return cartService;
    }

    @Bean
    public UpdateCartItemUseCase updateCartItemUseCase(CartServiceImpl cartService) {
        return cartService;
    }

    @Bean
    public RemoveFromCartUseCase removeFromCartUseCase(CartServiceImpl cartService) {
        return cartService;
    }

    @Bean
    public ClearCartUseCase clearCartUseCase(CartServiceImpl cartService) {
        return cartService;
    }
}