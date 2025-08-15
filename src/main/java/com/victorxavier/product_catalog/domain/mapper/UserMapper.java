package com.victorxavier.product_catalog.domain.mapper;

import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.UserEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.RoleEntity;
import java.util.stream.Collectors;

public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        user.setId(entity.getId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setBirthDate(entity.getBirthDate());
        
        if (entity.getRoles() != null) {
            user.setRoles(entity.getRoles().stream()
                    .map(roleMapper::toDomain)
                    .collect(Collectors.toSet()));
        }
        
        return user;
    }

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setBirthDate(user.getBirthDate());
        
        if (user.getRoles() != null) {
            entity.setRoles(user.getRoles().stream()
                    .map(roleMapper::toEntity)
                    .collect(Collectors.toSet()));
        }
        
        return entity;
    }
}