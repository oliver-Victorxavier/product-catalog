package com.victorxavier.product_catalog.infrastructure.mapper;

import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.RoleEntity;

public class RoleMapper {

    public Role toDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        Role role = new Role();
        role.setId(entity.getRoleId());
        role.setName(entity.getName());
        return role;
    }

    public RoleEntity toEntity(Role role) {
        if (role == null) {
            return null;
        }
        RoleEntity entity = new RoleEntity();
        entity.setRoleId(role.getId());
        entity.setName(role.getName());
        return entity;
    }
}