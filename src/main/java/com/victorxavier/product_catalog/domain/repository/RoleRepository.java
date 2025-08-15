package com.victorxavier.product_catalog.domain.repository;

import com.victorxavier.product_catalog.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);

    Role save(Role role);

    Optional<Role> findById(Long id);
}