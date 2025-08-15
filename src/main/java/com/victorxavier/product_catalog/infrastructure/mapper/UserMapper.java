package com.victorxavier.product_catalog.infrastructure.mapper;

import com.victorxavier.product_catalog.domain.dto.UserDTO;
import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.mapper.UserDomainMapper;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.RoleEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper implements UserDomainMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        user.setId(entity.getUserId());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setBirthDate(entity.getBirthDate());
        user.setUsername(entity.getUsername());
        user.setPasswordHash(entity.getPasswordHash());
        user.setPasswordSalt(entity.getPasswordSalt());
        user.setCreationTimestamp(entity.getCreationTimestamp());

        if (entity.getRoles() != null) {
            Set<Role> roles = entity.getRoles().stream()
                    .map(roleMapper::toDomain)
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return user;
    }

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setUserId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setBirthDate(user.getBirthDate());
        entity.setUsername(user.getUsername());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setPasswordSalt(user.getPasswordSalt());
        entity.setCreationTimestamp(user.getCreationTimestamp());

        if (user.getRoles() != null) {
            Set<RoleEntity> roleEntities = user.getRoles().stream()
                    .map(roleMapper::toEntity)
                    .collect(Collectors.toSet());
            entity.setRoles(roleEntities);
        }

        return entity;
    }

    @Override
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        Set<String> roleNames = null;
        if (user.getRoles() != null) {
            roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());
        }

        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthDate(),
                user.getUsername(),
                user.getCreationTimestamp(),
                roleNames
        );
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.id());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setEmail(userDTO.email());
        user.setBirthDate(userDTO.birthDate());
        user.setUsername(userDTO.username());
        user.setCreationTimestamp(userDTO.creationTimestamp());

        // Note: Role conversion from String names would require additional logic
        // This is a simplified implementation
        return user;
    }
}