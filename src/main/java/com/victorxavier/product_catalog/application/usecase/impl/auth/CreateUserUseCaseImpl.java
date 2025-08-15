package com.victorxavier.product_catalog.application.usecase.impl.auth;

import com.victorxavier.product_catalog.domain.dto.CreateUserDto;
import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.repository.RoleRepository;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.usecase.auth.CreateUserUseCase;

import java.time.Instant;
import java.util.Optional;
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
    public void createUser(CreateUserDto createUserDto) {
        validateUserDoesNotExist(createUserDto.username(), createUserDto.email());
        
        String salt = passwordService.generateSalt();
        String hashedPassword = passwordService.hashPassword(createUserDto.password(), salt);
        
        User user = new User();
        user.setFirstName(createUserDto.firstName());
        user.setLastName(createUserDto.lastName());
        user.setEmail(createUserDto.email());
        user.setBirthDate(createUserDto.birthDate());
        user.setUsername(createUserDto.username());
        user.setPasswordHash(hashedPassword);
        user.setPasswordSalt(salt);
        user.setCreationTimestamp(Instant.now());
        
        if (createUserDto.roles() != null && !createUserDto.roles().isEmpty()) {
            assignRoles(user, createUserDto.roles());
        } else {
            assignDefaultRole(user);
        }
        
        userRepository.save(user);
    }

    private void validateUserDoesNotExist(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
    }

    private void assignDefaultRole(User user) {
        Optional<Role> userRole = roleRepository.findByName("USER");
        if (userRole.isPresent()) {
            user.addRole(userRole.get());
        } else {
            throw new IllegalStateException("Default USER role not found in database");
        }
    }
    
    private void assignRoles(User user, Set<String> roleNames) {
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            user.addRole(role);
        }
    }
}