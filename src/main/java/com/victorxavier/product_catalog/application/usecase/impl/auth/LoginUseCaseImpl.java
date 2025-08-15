package com.victorxavier.product_catalog.application.usecase.impl.auth;

import com.victorxavier.product_catalog.domain.dto.LoginRequest;
import com.victorxavier.product_catalog.domain.dto.LoginResponse;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.usecase.auth.LoginUseCase;
import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.service.JwtService;
import com.victorxavier.product_catalog.domain.exception.AuthenticationException;

import java.time.Instant;
import java.util.stream.Collectors;

public class LoginUseCaseImpl implements LoginUseCase {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public LoginUseCaseImpl(JwtService jwtService, UserRepository userRepository, PasswordService passwordService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new AuthenticationException("user or password is invalid!"));

        if (!passwordService.verifyPassword(loginRequest.password(), user.getPasswordHash(), user.getPasswordSalt())) {
            throw new AuthenticationException("user or password is invalid!");
        }

        Instant now = Instant.now();
        long expiresIn = 300L; // 5 minutes

        var scopes = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .toList();

        String jwtValue = jwtService.generateToken(
                user.getUserId().toString(),
                scopes,
                now,
                now.plusSeconds(expiresIn)
        );

        return new LoginResponse(jwtValue, expiresIn);
    }
}