package com.victorxavier.product_catalog.infrastructure.mapper;

import com.victorxavier.product_catalog.domain.dto.RoleDTO;
import com.victorxavier.product_catalog.domain.dto.UserDTO;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.domain.mapper.UserDomainMapper;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.UserEntity;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.RoleEntity;

import java.util.Set;
import java.util.stream.Collectors;

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
        user.setUsername(entity.getUsername());
        user.setPasswordHash(entity.getPasswordHash());
        user.setPasswordSalt(entity.getPasswordSalt());
        user.setBirthDate(entity.getBirthDate());
        user.setCreationTimestamp(entity.getCreationTimestamp());
        
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
        entity.setUserId(user.getId());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setUsername(user.getUsername());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setPasswordSalt(user.getPasswordSalt());
        entity.setBirthDate(user.getBirthDate());
        entity.setCreationTimestamp(user.getCreationTimestamp());
        
        if (user.getRoles() != null) {
            entity.setRoles(user.getRoles().stream()
                    .map(roleMapper::toEntity)
                    .collect(Collectors.toSet()));
        }
        
        return entity;
    }

    @Override
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        Set<RoleDTO> roleDTOs = null;
        if (user.getRoles() != null) {
            roleDTOs = user.getRoles().stream()
                    .map(role -> new RoleDTO(role.getId().toString(), role.getName()))
                    .collect(Collectors.toSet());
        }
        
        return new UserDTO(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getUsername(),
            user.getBirthDate(),
            user.getCreationTimestamp(),
            roleDTOs
        );
    }

    @Override
    public User toDomain(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.id());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setBirthDate(userDTO.birthDate());
        user.setCreationTimestamp(userDTO.creationTimestamp());
        
        if (userDTO.roles() != null) {
            Set<Role> roles = userDTO.roles().stream()
                    .map(roleDTO -> {
                        Role role = new Role();
                        role.setId(Long.parseLong(roleDTO.id()));
                        role.setName(roleDTO.name());
                        return role;
                    })
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        
        return user;
    }
}