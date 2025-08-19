package com.victorxavier.product_catalog.domain.usecase;

import com.victorxavier.product_catalog.domain.dto.CartDTO;

public interface AddToCartUseCase {
    
    /**
     * Adiciona um produto ao carrinho do usuário
     * @param userId o ID do usuário
     * @param productId o ID do produto
     * @param quantity a quantidade a ser adicionada
     * @return o carrinho atualizado
     */
    CartDTO addToCart(String userId, Long productId, Integer quantity);
}