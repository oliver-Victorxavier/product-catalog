package com.victorxavier.product_catalog.infrastructure.persistence.impl;

import com.victorxavier.product_catalog.domain.entity.Cart;
import com.victorxavier.product_catalog.domain.repository.CartRepository;
import com.victorxavier.product_catalog.infrastructure.mapper.CartRedisMapper;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.CartRedisEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.repository.CartRedisRepository;

import java.util.Optional;

public class CartRepositoryImpl implements CartRepository {

    private final CartRedisRepository cartRedisRepository;

    public CartRepositoryImpl(CartRedisRepository cartRedisRepository) {
        this.cartRedisRepository = cartRedisRepository;
    }

    @Override
    public Cart save(Cart cart) {
        cart.updateTimestamp(); // Atualiza o timestamp antes de salvar
        CartRedisEntity redisEntity = CartRedisMapper.toRedisEntity(cart);
        CartRedisEntity savedEntity = cartRedisRepository.save(redisEntity);
        return CartRedisMapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<Cart> findByUserId(String userId) {
        return cartRedisRepository.findByUserId(userId)
                .map(CartRedisMapper::toDomainEntity);
    }

    @Override
    public Optional<Cart> findById(String cartId) {
        return cartRedisRepository.findById(cartId)
                .map(CartRedisMapper::toDomainEntity);
    }

    @Override
    public void deleteByUserId(String userId) {
        cartRedisRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteById(String cartId) {
        cartRedisRepository.deleteById(cartId);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return cartRedisRepository.existsByUserId(userId);
    }
}