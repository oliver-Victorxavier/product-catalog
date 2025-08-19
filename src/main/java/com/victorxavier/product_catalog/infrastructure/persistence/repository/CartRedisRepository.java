package com.victorxavier.product_catalog.infrastructure.persistence.repository;

import com.victorxavier.product_catalog.infrastructure.persistence.entity.CartRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRedisRepository extends CrudRepository<CartRedisEntity, String> {
    
    /**
     * Busca um carrinho pelo ID do usuário
     * @param userId o ID do usuário
     * @return o carrinho do usuário, se existir
     */
    Optional<CartRedisEntity> findByUserId(String userId);
    
    /**
     * Remove um carrinho pelo ID do usuário
     * @param userId o ID do usuário
     */
    void deleteByUserId(String userId);
    
    /**
     * Verifica se existe um carrinho para o usuário
     * @param userId o ID do usuário
     * @return true se existe um carrinho para o usuário
     */
    boolean existsByUserId(String userId);
}