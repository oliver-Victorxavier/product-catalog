package com.victorxavier.product_catalog.infrastructure.config;

import com.victorxavier.product_catalog.application.usecase.impl.auth.CreateUserUseCaseImpl;
import com.victorxavier.product_catalog.application.usecase.impl.auth.LoginUseCaseImpl;
import com.victorxavier.product_catalog.application.usecase.impl.user.UserServiceImpl;
import com.victorxavier.product_catalog.domain.repository.RoleRepository;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.usecase.auth.CreateUserUseCase;
import com.victorxavier.product_catalog.domain.usecase.auth.LoginUseCase;
import com.victorxavier.product_catalog.domain.usecase.user.UserService;
import com.victorxavier.product_catalog.domain.service.JwtService;
import com.victorxavier.product_catalog.infrastructure.security.PasswordServiceAdapter;
import com.victorxavier.product_catalog.domain.service.SecurityService;
import com.victorxavier.product_catalog.domain.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public LoginUseCase loginUseCase(JwtService jwtService, UserRepository userRepository, PasswordServiceAdapter passwordService) {
        return new LoginUseCaseImpl(jwtService, userRepository, passwordService);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository, RoleRepository roleRepository, PasswordServiceAdapter passwordService) {
        return new CreateUserUseCaseImpl(userRepository, roleRepository, passwordService);
    }

    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository, 
                                  PasswordServiceAdapter passwordService, SecurityService securityService, UserMapper userMapper) {
        return new UserServiceImpl(userRepository, roleRepository, passwordService, securityService, userMapper);
    }
}