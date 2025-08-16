package com.victorxavier.product_catalog.infrastructure.config;

import com.victorxavier.product_catalog.domain.mapper.CategoryDomainMapper;
import com.victorxavier.product_catalog.domain.mapper.UserDomainMapper;
import com.victorxavier.product_catalog.infrastructure.mapper.CategoryDTOMapper;
import com.victorxavier.product_catalog.infrastructure.mapper.UserMapper;
import com.victorxavier.product_catalog.infrastructure.mapper.RoleMapper;
import com.victorxavier.product_catalog.application.usecase.impl.category.CategoryServiceImpl;
import com.victorxavier.product_catalog.application.usecase.impl.product.ProductServiceImpl;
import com.victorxavier.product_catalog.application.usecase.impl.auth.LoginUseCaseImpl;
import com.victorxavier.product_catalog.application.usecase.impl.auth.CreateUserUseCaseImpl;
import com.victorxavier.product_catalog.application.usecase.impl.user.UserServiceImpl;
import com.victorxavier.product_catalog.domain.repository.CategoryRepository;
import com.victorxavier.product_catalog.domain.repository.ProductRepository;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.repository.RoleRepository;
import com.victorxavier.product_catalog.domain.service.PasswordService;
import com.victorxavier.product_catalog.domain.service.JwtService;
import com.victorxavier.product_catalog.domain.service.SecurityService;
import com.victorxavier.product_catalog.infrastructure.security.JwtServiceAdapter;
import com.victorxavier.product_catalog.infrastructure.security.SecurityServiceAdapter;
import com.victorxavier.product_catalog.domain.usecase.auth.LoginUseCase;
import com.victorxavier.product_catalog.domain.usecase.auth.CreateUserUseCase;
import com.victorxavier.product_catalog.domain.usecase.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ProductServiceImpl productServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {
        return new ProductServiceImpl(productRepository, categoryRepository);
    }

    @Bean
    public CategoryServiceImpl categoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryDomainMapper categoryDomainMapper) {
        return new CategoryServiceImpl(categoryRepository, categoryDomainMapper);
    }

    @Bean
    public CategoryDomainMapper categoryDomainMapper() {
        return new CategoryDTOMapper();
    }

    @Bean
    public RoleMapper roleMapper() {
        return new RoleMapper();
    }

    @Bean
    public UserMapper userMapper(RoleMapper roleMapper) {
        return new UserMapper(roleMapper);
    }

    @Bean
    public UserDomainMapper userDomainMapper(UserMapper userMapper) {
        return userMapper;
    }

    @Bean
    public LoginUseCase loginUseCase(
            UserRepository userRepository,
            PasswordService passwordService,
            JwtService jwtService) {
        return new LoginUseCaseImpl(userRepository, passwordService, jwtService);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordService passwordService) {
        return new CreateUserUseCaseImpl(userRepository, roleRepository, passwordService);
    }

    @Bean
    public UserService userService(
            CreateUserUseCase createUserUseCase,
            UserRepository userRepository,
            SecurityService securityService) {
        return new UserServiceImpl(createUserUseCase, userRepository, securityService);
    }

    @Bean
    public SecurityService securityService(JwtServiceAdapter jwtService) {
        return new SecurityServiceAdapter(jwtService);
    }
}