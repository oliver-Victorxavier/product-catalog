package com.victorxavier.product_catalog.application.usecase.impl.auth;

import com.victorxavier.product_catalog.domain.dto.LoginRequest;
import com.victorxavier.product_catalog.domain.dto.LoginResponse;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.infrastructure.security.JwtServiceAdapter;
import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.usecase.auth.LoginUseCase;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JwtServiceAdapter jwtService;

    public LoginUseCaseImpl(UserRepository userRepository, PasswordService passwordService, JwtServiceAdapter jwtService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("Login attempt for username: " + loginRequest.username());
        
        // Buscar usuário por username (que pode ser email)
        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> {
                    System.out.println("User not found: " + loginRequest.username());
                    return new IllegalArgumentException("Invalid credentials");
                });
        
        System.out.println("User found: " + user.getUsername() + ", ID: " + user.getId());
        System.out.println("User has salt: " + (user.getPasswordSalt() != null));
        System.out.println("User has hash: " + (user.getPasswordHash() != null));

        // Verificar senha
        boolean passwordValid = passwordService.verifyPassword(loginRequest.password(), user.getPasswordSalt(), user.getPasswordHash());
        System.out.println("Password verification result: " + passwordValid);
        
        if (!passwordValid) {
            System.out.println("Password verification failed for user: " + loginRequest.username());
            throw new IllegalArgumentException("Invalid credentials");
        }

        // Gerar token JWT
        String token = jwtService.generateToken(user);
        System.out.println("JWT token generated successfully for user: " + user.getUsername());

        return new LoginResponse(
            token,
            "Bearer",
            3600L,
            user.getUsername(),
            user.getEmail()
        );
    }
}