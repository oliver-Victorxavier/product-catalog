package com.victorxavier.product_catalog.domain.usecase.auth;

import com.victorxavier.product_catalog.domain.dto.CreateUserDto;

public interface CreateUserUseCase {
    void createUser(CreateUserDto createUserDto);
}