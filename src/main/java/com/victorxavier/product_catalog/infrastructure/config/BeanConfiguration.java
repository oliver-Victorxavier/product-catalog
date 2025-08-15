package com.victorxavier.product_catalog.infrastructure.config;

import com.victorxavier.product_catalog.domain.mapper.CategoryDomainMapper;
import com.victorxavier.product_catalog.domain.mapper.UserDomainMapper;
import com.victorxavier.product_catalog.infrastructure.mapper.CategoryDTOMapper;
import com.victorxavier.product_catalog.infrastructure.mapper.UserMapper;
import com.victorxavier.product_catalog.infrastructure.mapper.RoleMapper;
import com.victorxavier.product_catalog.application.usecase.impl.category.CategoryServiceImpl;
import com.victorxavier.product_catalog.application.usecase.impl.product.ProductServiceImpl;
import com.victorxavier.product_catalog.domain.repository.CategoryRepository;
import com.victorxavier.product_catalog.domain.repository.ProductRepository;
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
}