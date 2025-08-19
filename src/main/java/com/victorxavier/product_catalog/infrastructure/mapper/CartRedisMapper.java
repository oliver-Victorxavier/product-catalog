package com.victorxavier.product_catalog.infrastructure.mapper;

import com.victorxavier.product_catalog.domain.entity.Cart;
import com.victorxavier.product_catalog.domain.entity.CartItem;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.CartItemRedisEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.CartRedisEntity;

import java.util.List;

public class CartRedisMapper {

    public static CartRedisEntity toRedisEntity(Cart cart) {
        if (cart == null) {
            return null;
        }

        CartRedisEntity redisEntity = new CartRedisEntity(cart.getUserId());
        redisEntity.setId(cart.getId());
        redisEntity.setCreatedAt(cart.getCreatedAt());
        redisEntity.setUpdatedAt(cart.getUpdatedAt());

        List<CartItemRedisEntity> redisItems = cart.getItems().stream()
                .map(CartRedisMapper::toRedisEntity)
                .toList();
        
        redisEntity.setItems(redisItems);
        
        return redisEntity;
    }

    public static Cart toDomainEntity(CartRedisEntity redisEntity) {
        if (redisEntity == null) {
            return null;
        }

        Cart cart = new Cart(redisEntity.getUserId());
        cart.setId(redisEntity.getId());
        cart.setCreatedAt(redisEntity.getCreatedAt());
        cart.setUpdatedAt(redisEntity.getUpdatedAt());

        // Adiciona os itens ao carrinho usando o método addItem()
        redisEntity.getItems().forEach(redisItem -> {
            CartItem domainItem = toDomainEntity(redisItem);
            cart.addItem(domainItem);
        });

        return cart;
    }

    public static CartItemRedisEntity toRedisEntity(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }

        CartItemRedisEntity redisEntity = new CartItemRedisEntity(
                cartItem.getProductId(),
                cartItem.getProductName(),
                cartItem.getUnitPrice(),
                cartItem.getQuantity(),
                cartItem.getProductImageUrl()
        );
        redisEntity.setAddedAt(cartItem.getAddedAt());
        
        return redisEntity;
    }

    public static CartItem toDomainEntity(CartItemRedisEntity redisEntity) {
        if (redisEntity == null) {
            return null;
        }

        CartItem cartItem = new CartItem(
                redisEntity.getProductId(),
                redisEntity.getProductName(),
                redisEntity.getUnitPrice(),
                redisEntity.getQuantity(),
                redisEntity.getProductImageUrl()
        );
        cartItem.setAddedAt(redisEntity.getAddedAt());
        
        return cartItem;
    }
}