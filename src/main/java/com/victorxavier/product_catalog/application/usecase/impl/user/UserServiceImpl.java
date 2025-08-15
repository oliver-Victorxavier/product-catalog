package com.victorxavier.product_catalog.application.usecase.impl.user;

import com.victorxavier.product_catalog.domain.dto.UserDTO;
import com.victorxavier.product_catalog.domain.dto.UserInsertDTO;
import com.victorxavier.product_catalog.domain.dto.UserUpdateDto;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;
import com.victorxavier.product_catalog.domain.usecase.user.UserService;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;

public class UserServiceImpl implements UserService {

    @Override
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        // TODO: Implementar busca paginada de usuários
        return new Page<>(Collections.emptyList(), 0, pageable.getPageSize(), 0L);
    }

    @Override
    public UserDTO findById(String id) {
        // TODO: Implementar busca por ID
        return new UserDTO(
            id,
            "Mock",
            "User",
            "mock@email.com",
            null,
            "mockuser",
            Instant.now(),
            Set.of("USER")
        );
    }

    @Override
    public UserDTO findMe() {
        // TODO: Implementar busca do usuário atual
        return new UserDTO(
            "current-user-id",
            "Current",
            "User",
            "current@email.com",
            null,
            "currentuser",
            Instant.now(),
            Set.of("USER")
        );
    }

    @Override
    public UserDTO insert(UserInsertDTO dto) {
        // TODO: Implementar inserção de usuário
        return new UserDTO(
            "new-user-id",
            dto.firstName(),
            dto.lastName(),
            dto.email(),
            dto.birthDate(),
            dto.username(),
            Instant.now(),
            dto.roles()
        );
    }

    @Override
    public UserDTO update(String id, UserUpdateDto dto) {
        // TODO: Implementar atualização de usuário
        return new UserDTO(
            id,
            dto.firstName(),
            dto.lastName(),
            dto.email(),
            dto.birthDate(),
            "updated-username",
            Instant.now(),
            dto.roles()
        );
    }

    @Override
    public void delete(String id) {
        // TODO: Implementar exclusão de usuário
        System.out.println("Deleting user with ID: " + id);
    }
}