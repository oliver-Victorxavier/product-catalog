package com.victorxavier.product_catalog.domain.usecase;

import com.victorxavier.product_catalog.domain.dto.CartDTO;

import java.util.Optional;

public interface GetCartUseCase {
    
    /**
     * Busca o carrinho do usuário
     * @param userId o ID do usuário
     * @return o carrinho do usuário, se existir
     */
    Optional<CartDTO> getCartByUserId(String userId);
}