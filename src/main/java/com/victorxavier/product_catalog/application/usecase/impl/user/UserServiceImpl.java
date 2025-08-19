package com.victorxavier.product_catalog.application.usecase.impl.user;

import com.victorxavier.product_catalog.domain.dto.CreateUserDto;
import com.victorxavier.product_catalog.domain.dto.UserDTO;
import com.victorxavier.product_catalog.domain.dto.UserInsertDTO;
import com.victorxavier.product_catalog.domain.dto.UserUpdateDto;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.Pageable;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.service.SecurityService;
import com.victorxavier.product_catalog.domain.usecase.auth.CreateUserUseCase;
import com.victorxavier.product_catalog.domain.usecase.user.UserService;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final CreateUserUseCase createUserUseCase;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public UserServiceImpl(CreateUserUseCase createUserUseCase, UserRepository userRepository, SecurityService securityService) {
        this.createUserUseCase = createUserUseCase;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        
        var userDTOs = userPage.getContent().stream()
            .map(user -> new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthDate(),
                user.getUsername(),
                user.getCreationTimestamp(),
                user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toSet())
            ))
            .collect(Collectors.toList());
        
        return new Page<>(userDTOs, userPage.getPageNumber(), userPage.getPageSize(), userPage.getTotalElements());
    }

    @Override
    public Page<UserDTO> findAdminsPaged(Pageable pageable) {
        Page<User> userPage = userRepository.findByRoleName("ROLE_ADMIN", pageable);
        
        var userDTOs = userPage.getContent().stream()
            .map(user -> new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthDate(),
                user.getUsername(),
                user.getCreationTimestamp(),
                user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toSet())
            ))
            .collect(Collectors.toList());
        
        return new Page<>(userDTOs, userPage.getPageNumber(), userPage.getPageSize(), userPage.getTotalElements());
    }

    @Override
    public UserDTO findById(String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        return new UserDTO(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getBirthDate(),
            user.getUsername(),
            user.getCreationTimestamp(),
            user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet())
        );
    }

    @Override
    public UserDTO findMe(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        
        return new UserDTO(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getBirthDate(),
            user.getUsername(),
            user.getCreationTimestamp(),
            user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet())
        );
    }

    @Override
    public UserDTO registerUser(UserInsertDTO dto) {
        // Registro público - sempre cria usuário com role USER, ignorando roles do DTO
        CreateUserDto createUserDto = new CreateUserDto(
            dto.firstName(),
            dto.lastName(),
            dto.email(),
            dto.birthDate(),
            dto.username(),
            dto.password(),
            null // Não passa roles - será atribuída a role padrão USER
        );
        
        createUserUseCase.createUser(createUserDto);
        
        User createdUser = userRepository.findByUsername(dto.username())
            .orElseThrow(() -> new RuntimeException("User not found after creation"));
        
        return new UserDTO(
            createdUser.getId(),
            createdUser.getFirstName(),
            createdUser.getLastName(),
            createdUser.getEmail(),
            createdUser.getBirthDate(),
            createdUser.getUsername(),
            createdUser.getCreationTimestamp(),
            createdUser.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet())
        );
    }

    @Override
    public UserDTO insert(UserInsertDTO dto) {
        CreateUserDto createUserDto = new CreateUserDto(
            dto.firstName(),
            dto.lastName(),
            dto.email(),
            dto.birthDate(),
            dto.username(),
            dto.password(),
            dto.roles()
        );
        
        createUserUseCase.createUser(createUserDto);
        
        User createdUser = userRepository.findByUsername(dto.username())
            .orElseThrow(() -> new RuntimeException("User not found after creation"));
        
        return new UserDTO(
            createdUser.getId(),
            createdUser.getFirstName(),
            createdUser.getLastName(),
            createdUser.getEmail(),
            createdUser.getBirthDate(),
            createdUser.getUsername(),
            createdUser.getCreationTimestamp(),
            createdUser.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet())
        );
    }

    @Override
    public UserDTO insertAdmin(UserInsertDTO dto) {
        Set<String> adminRoles = Set.of("ROLE_ADMIN");
        
        CreateUserDto createUserDto = new CreateUserDto(
            dto.firstName(),
            dto.lastName(),
            dto.email(),
            dto.birthDate(),
            dto.username(),
            dto.password(),
            adminRoles
        );
        
        createUserUseCase.createUser(createUserDto);
        
        User createdUser = userRepository.findByUsername(dto.username())
            .orElseThrow(() -> new RuntimeException("Admin user not found after creation"));
        
        return new UserDTO(
            createdUser.getId(),
            createdUser.getFirstName(),
            createdUser.getLastName(),
            createdUser.getEmail(),
            createdUser.getBirthDate(),
            createdUser.getUsername(),
            createdUser.getCreationTimestamp(),
            createdUser.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet())
        );
    }

    @Override
    public UserDTO update(String id, UserUpdateDto dto) {

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

        System.out.println("Deleting user with ID: " + id);
    }
}