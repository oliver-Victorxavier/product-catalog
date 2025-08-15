package com.victorxavier.product_catalog.application.usecase.impl.user;

import com.victorxavier.product_catalog.domain.dto.UserDTO;
import com.victorxavier.product_catalog.domain.dto.UserInsertDTO;
import com.victorxavier.product_catalog.domain.dto.UserUpdateDto;
import com.victorxavier.product_catalog.domain.entity.Role;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.mapper.UserDomainMapper;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;
import com.victorxavier.product_catalog.domain.repository.RoleRepository;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.service.SecurityService;
import com.victorxavier.product_catalog.domain.usecase.user.UserService;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final SecurityService securityService;
    private final UserDomainMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, 
                          PasswordService passwordService, SecurityService securityService, UserDomainMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordService = passwordService;
        this.securityService = securityService;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDTO);
    }

    @Override
    public UserDTO findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO findMe() {
        String currentUserId = securityService.getCurrentUserId();
        return findById(currentUserId);
    }

    @Override
    public UserDTO insert(UserInsertDTO dto) {
        String salt = passwordService.generateSalt();
        String hashedPassword = passwordService.hashPassword(dto.password(), salt);
        
        Set<Role> roles = dto.roleIds().stream()
                .map(roleId -> roleRepository.findById(Long.valueOf(roleId)))
                .map(optionalRole -> optionalRole.orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());
        
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setUsername(dto.username());
        user.setPasswordSalt(salt);
        user.setPasswordHash(hashedPassword);
        user.setBirthDate(dto.birthDate());
        user.setCreationTimestamp(Instant.now());
        user.setRoles(roles);
        
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO update(String id, UserUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setBirthDate(dto.birthDate());
        
        if (dto.roleIds() != null && !dto.roleIds().isEmpty()) {
            Set<Role> roles = dto.roleIds().stream()
                    .map(roleId -> roleRepository.findById(Long.valueOf(roleId)))
                    .map(optionalRole -> optionalRole.orElseThrow(() -> new RuntimeException("Role not found")))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }
        
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }


}