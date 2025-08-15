package com.victorxavier.product_catalog.domain.repository;

import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;

import java.util.Optional;

public interface UserRepository {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findById(String id);
    
    User save(User user);
    
    void deleteById(String id);
    
    Page<User> findAll(Pageable pageable);
    
    boolean existsById(String id);
}