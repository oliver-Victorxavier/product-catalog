package com.victorxavier.product_catalog.domain.repository;

import com.victorxavier.product_catalog.domain.entity.Cart;

import java.util.Optional;

public interface CartRepository {
    
    /**
     * Salva ou atualiza um carrinho
     * @param cart o carrinho a ser salvo
     * @return o carrinho salvo
     */
    Cart save(Cart cart);
    
    /**
     * Busca um carrinho pelo ID do usuário
     * @param userId o ID do usuário
     * @return o carrinho do usuário, se existir
     */
    Optional<Cart> findByUserId(String userId);
    
    /**
     * Busca um carrinho pelo seu ID
     * @param cartId o ID do carrinho
     * @return o carrinho, se existir
     */
    Optional<Cart> findById(String cartId);
    
    /**
     * Remove um carrinho pelo ID do usuário
     * @param userId o ID do usuário
     */
    void deleteByUserId(String userId);
    
    /**
     * Remove um carrinho pelo seu ID
     * @param cartId o ID do carrinho
     */
    void deleteById(String cartId);
    
    /**
     * Verifica se existe um carrinho para o usuário
     * @param userId o ID do usuário
     * @return true se existe um carrinho para o usuário
     */
    boolean existsByUserId(String userId);
}