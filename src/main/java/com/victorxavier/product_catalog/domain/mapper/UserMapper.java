package com.victorxavier.product_catalog.domain.mapper;

import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.UserEntity;
import com.victorxavier.product_catalog.domain.mapper.RoleMapper;

import java.util.stream.Collectors;

public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        
        User user = new User();
        user.setUserId(userEntity.getUserId());
        user.setUsername(userEntity.getUsername());
        user.setPasswordHash(userEntity.getPasswordHash());
        user.setPasswordSalt(userEntity.getPasswordSalt());
        user.setCreationTimestamp(userEntity.getCreationTimestamp());
        
        if (userEntity.getRoles() != null) {
            user.setRoles(userEntity.getRoles().stream()
                    .map(roleMapper::toDomain)
                    .collect(Collectors.toSet()));
        }
        
        return user;
    }

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setUsername(user.getUsername());
        userEntity.setPasswordHash(user.getPasswordHash());
        userEntity.setPasswordSalt(user.getPasswordSalt());
        userEntity.setCreationTimestamp(user.getCreationTimestamp());
        
        if (user.getRoles() != null) {
            userEntity.setRoles(user.getRoles().stream()
                    .map(roleMapper::toEntity)
                    .collect(Collectors.toSet()));
        }
        
        return userEntity;
    }
}