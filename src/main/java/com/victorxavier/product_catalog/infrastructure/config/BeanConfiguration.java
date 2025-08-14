package com.victorxavier.product_catalog.infrastructure.config;

import com.victorxavier.product_catalog.application.mapper.CategoryDTOMapper;
import com.victorxavier.product_catalog.application.usecase.impl.category.CategoryServiceImpl;
import com.victorxavier.product_catalog.application.usecase.impl.product.ProductServiceImpl;
import com.victorxavier.product_catalog.domain.repository.CategoryRepository;
import com.victorxavier.product_catalog.domain.repository.ProductRepository;
import com.victorxavier.product_catalog.domain.usecase.category.*;
import com.victorxavier.product_catalog.domain.usecase.product.*;
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
    public FindProductUseCase findProductUseCase(ProductServiceImpl productServiceImpl) {

        return productServiceImpl;
    }

    @Bean
    public CreateProductUsecase createProductUseCase(ProductServiceImpl productServiceImpl) {
        return productServiceImpl;
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(ProductServiceImpl productServiceImpl) {
        return productServiceImpl;
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductServiceImpl productServiceImpl) {
        return productServiceImpl;
    }

    @Bean
    public CategoryServiceImpl categoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryDTOMapper categoryDTOMapper) {
        return new CategoryServiceImpl(categoryRepository, categoryDTOMapper);
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase(CategoryServiceImpl categoryServiceImpl) {
        return categoryServiceImpl;
    }

    @Bean
    public FindCategoryUseCase findCategoryUseCase(CategoryServiceImpl categoryServiceImpl) {
        return categoryServiceImpl;
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase(CategoryServiceImpl categoryServiceImpl) {
        return categoryServiceImpl;
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase(CategoryServiceImpl categoryServiceImpl) {
        return categoryServiceImpl;
    }

    @Bean
    public CategoryDTOMapper categoryDTOMapper() {
        return new CategoryDTOMapper();
    }
}