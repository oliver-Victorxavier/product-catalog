package com.victorxavier.product_catalog.infrastructure.config;

import com.victorxavier.product_catalog.domain.mapper.CategoryDTOMapper;
import com.victorxavier.product_catalog.domain.mapper.UserMapper;
import com.victorxavier.product_catalog.domain.mapper.RoleMapper;
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
            CategoryDTOMapper categoryDTOMapper) {
        return new CategoryServiceImpl(categoryRepository, categoryDTOMapper);
    }

    @Bean
    public CategoryDTOMapper categoryDTOMapper() {
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
}