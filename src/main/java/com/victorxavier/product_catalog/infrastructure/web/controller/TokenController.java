package com.victorxavier.product_catalog.infrastructure.web.controller;

import com.victorxavier.product_catalog.domain.dto.LoginRequest;
import com.victorxavier.product_catalog.domain.dto.LoginResponse;
import com.victorxavier.product_catalog.domain.usecase.auth.LoginUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class TokenController {

    private final LoginUseCase loginUseCase;

    public TokenController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Login attempt for user: " + loginRequest.username());
            var loginResponse = loginUseCase.login(loginRequest);
            System.out.println("Login successful for user: " + loginRequest.username());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            System.err.println("Login failed for user: " + loginRequest.username() + ", Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}