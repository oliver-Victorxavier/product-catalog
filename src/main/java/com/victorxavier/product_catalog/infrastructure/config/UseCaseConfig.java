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
import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.service.SecurityService;
import com.victorxavier.product_catalog.domain.mapper.UserDomainMapper;
import com.victorxavier.product_catalog.infrastructure.security.SecurityServiceAdapter;
import com.victorxavier.product_catalog.infrastructure.security.JwtServiceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public LoginUseCase loginUseCase(JwtService jwtService, UserRepository userRepository, PasswordService passwordService) {
        return new LoginUseCaseImpl(jwtService, userRepository, passwordService);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository, RoleRepository roleRepository, PasswordService passwordService) {
        return new CreateUserUseCaseImpl(userRepository, roleRepository, passwordService);
    }

    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository, 
                                  PasswordService passwordService, SecurityService securityService, UserDomainMapper userDomainMapper) {
        return new UserServiceImpl(userRepository, roleRepository, passwordService, securityService, userDomainMapper);
    }

    @Bean
    public PasswordService passwordService() {
        return new PasswordServiceAdapter();
    }

    @Bean
    public SecurityService securityService() {
        return new SecurityServiceAdapter();
    }

    // JwtService é gerenciado automaticamente pela anotação @Service no JwtServiceAdapter
}