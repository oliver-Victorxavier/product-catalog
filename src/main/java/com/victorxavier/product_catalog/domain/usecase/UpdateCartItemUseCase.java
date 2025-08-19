package com.victorxavier.product_catalog.domain.usecase;

import com.victorxavier.product_catalog.domain.dto.CartDTO;

public interface UpdateCartItemUseCase {
    
    /**
     * Atualiza a quantidade de um item no carrinho
     * @param userId o ID do usuário
     * @param productId o ID do produto
     * @param quantity a nova quantidade
     * @return o carrinho atualizado
     */
    CartDTO updateCartItem(String userId, Long productId, Integer quantity);
}