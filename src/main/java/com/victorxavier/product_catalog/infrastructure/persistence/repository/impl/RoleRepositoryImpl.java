package com.victorxavier.product_catalog.infrastructure.persistence.repository.impl;

import com.victorxavier.product_catalog.infrastructure.mapper.RoleMapper;
import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.domain.repository.RoleRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.repository.JpaRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final JpaRoleRepository jpaRoleRepository;
    private final RoleMapper roleMapper;

    public RoleRepositoryImpl(JpaRoleRepository jpaRoleRepository, RoleMapper roleMapper) {
        this.jpaRoleRepository = jpaRoleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRoleRepository.findByName(name)
                .map(roleMapper::toDomain);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpaRoleRepository.findById(id)
                .map(roleMapper::toDomain);
    }

    @Override
    public Role save(Role role) {
        var roleEntity = roleMapper.toEntity(role);
        var savedEntity = jpaRoleRepository.save(roleEntity);
        return roleMapper.toDomain(savedEntity);
    }
}