package com.victorxavier.product_catalog.domain.usecase.auth;

import com.victorxavier.product_catalog.domain.dto.LoginRequest;
import com.victorxavier.product_catalog.domain.dto.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}