package com.victorxavier.product_catalog.domain.usecase;

public interface ClearCartUseCase {
    
    /**
     * Limpa todos os itens do carrinho do usuário
     * @param userId o ID do usuário
     */
    void clearCart(String userId);
}