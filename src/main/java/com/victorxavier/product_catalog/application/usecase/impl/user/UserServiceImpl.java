package com.victorxavier.product_catalog.application.usecase.impl.user;

import com.victorxavier.product_catalog.domain.mapper.UserMapper;
import com.victorxavier.product_catalog.domain.dto.UserDTO;
import com.victorxavier.product_catalog.domain.dto.UserInsertDTO;
import com.victorxavier.product_catalog.domain.dto.UserUpdateDto;
import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.exception.ResourceNotFoundException;
import com.victorxavier.product_catalog.domain.repository.RoleRepository;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.usecase.user.UserService;
import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.service.SecurityService;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;
import com.victorxavier.product_catalog.domain.exception.ConflictException;
import com.victorxavier.product_catalog.domain.exception.InternalServerException;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final SecurityService securityService;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, 
                          PasswordService passwordService, SecurityService securityService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordService = passwordService;
        this.securityService = securityService;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::mapToDTO);
    }

    @Override
    public UserDTO findById(String id) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapToDTO(entity);
    }

    @Override
    public UserDTO findMe() {
        String username = securityService.getCurrentUsername();
        
        Optional<User> obj = userRepository.findByUsername(username);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToDTO(entity);
    }

    @Override
    public UserDTO insert(UserInsertDTO dto) {
        var userFromDb = userRepository.findByUsername(dto.username());
        if (userFromDb.isPresent()) {
            throw new ConflictException("Username already exists");
        }

        // Gera salt e hash para a senha
        var saltAndHash = passwordService.createSaltAndHash(dto.password());

        var user = new User();
        user.setUsername(dto.username());
        user.setPasswordSalt(saltAndHash.getSalt());
        user.setPasswordHash(saltAndHash.getHash());
        user.setCreationTimestamp(Instant.now());
        
        // Define roles
        Set<Role> roles = new HashSet<>();
        if (dto.roles() != null && !dto.roles().isEmpty()) {
            for (String roleName : dto.roles()) {
                Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));
                roles.add(role);
            }
        } else {
            // Se não especificado, adiciona role BASIC
            Role basicRole = roleRepository.findByName(Role.Values.BASIC.name())
                .orElseThrow(() -> new InternalServerException("Basic role not found"));
            roles.add(basicRole);
        }
        user.setRoles(roles);

        user = userRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO update(String id, UserUpdateDto dto) {
        Optional<User> obj = userRepository.findById(id);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        
        entity.setUsername(dto.username());
        entity = userRepository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User entity) {
        Set<String> roleNames = entity.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        
        return new UserDTO(
                entity.getUserId(),
                entity.getUsername(),
                entity.getCreationTimestamp(),
                roleNames
        );
    }
}