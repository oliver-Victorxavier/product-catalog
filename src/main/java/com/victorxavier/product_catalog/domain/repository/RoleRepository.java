package com.victorxavier.product_catalog.domain.repository;

import com.victorxavier.product_catalog.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
    Optional<Role> findById(Long id);
    Role save(Role role);
}