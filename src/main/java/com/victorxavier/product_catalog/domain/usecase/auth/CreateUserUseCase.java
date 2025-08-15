package com.victorxavier.product_catalog.domain.usecase.auth;

import com.victorxavier.product_catalog.domain.dto.CreateUserDto;
import com.victorxavier.product_catalog.domain.entity.User;

public interface CreateUserUseCase {
    User createUser(CreateUserDto createUserDto);
}