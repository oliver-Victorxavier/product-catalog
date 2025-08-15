package com.victorxavier.product_catalog.domain.mapper;

import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.RoleEntity;

public class RoleMapper {

    public Role toDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        Role role = new Role();
        role.setId(entity.getId());
        role.setAuthority(entity.getAuthority());
        
        return role;
    }

    public RoleEntity toEntity(Role role) {
        if (role == null) {
            return null;
        }

        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setAuthority(role.getAuthority());
        
        return entity;
    }
}