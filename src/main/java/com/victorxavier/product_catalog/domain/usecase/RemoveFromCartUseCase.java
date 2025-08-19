package com.victorxavier.product_catalog.domain.usecase;

import com.victorxavier.product_catalog.domain.dto.CartDTO;

public interface RemoveFromCartUseCase {
    
    /**
     * Remove um item do carrinho
     * @param userId o ID do usuário
     * @param productId o ID do produto a ser removido
     * @return o carrinho atualizado
     */
    CartDTO removeFromCart(String userId, Long productId);
}