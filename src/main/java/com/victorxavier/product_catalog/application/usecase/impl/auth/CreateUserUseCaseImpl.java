package com.victorxavier.product_catalog.application.usecase.impl.auth;

import com.victorxavier.product_catalog.domain.dto.CreateUserDto;
import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.repository.RoleRepository;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.usecase.auth.CreateUserUseCase;
import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.exception.ConflictException;
import com.victorxavier.product_catalog.domain.exception.InternalServerException;

import java.time.Instant;
import java.util.Set;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;

    public CreateUserUseCaseImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordService = passwordService;
    }

    @Override
    public User createUser(CreateUserDto createUserDto) {
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name())
                .orElseThrow(() -> new InternalServerException("Basic role not found"));

        var userFromDb = userRepository.findByUsername(createUserDto.username());
        if (userFromDb.isPresent()) {
            throw new ConflictException("Username already exists");
        }

        // Gera salt e hash para a senha
        var saltAndHash = passwordService.createSaltAndHash(createUserDto.password());

        var user = new User();
        user.setUsername(createUserDto.username());
        user.setPasswordSalt(saltAndHash.getSalt());
        user.setPasswordHash(saltAndHash.getHash());
        user.setRoles(Set.of(basicRole));
        user.setCreationTimestamp(Instant.now());

        return userRepository.save(user);
    }
}