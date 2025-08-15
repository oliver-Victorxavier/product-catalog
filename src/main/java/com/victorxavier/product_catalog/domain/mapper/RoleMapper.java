package com.victorxavier.product_catalog.domain.mapper;

import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.RoleEntity;

public class RoleMapper {

    public Role toDomain(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }
        return new Role(roleEntity.getRoleId(), roleEntity.getName());
    }

    public RoleEntity toEntity(Role role) {
        if (role == null) {
            return null;
        }
        return new RoleEntity(role.getRoleId(), role.getName());
    }
}