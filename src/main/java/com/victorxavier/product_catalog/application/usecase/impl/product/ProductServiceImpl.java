package com.victorxavier.product_catalog.application.usecase.impl.product;

import com.victorxavier.product_catalog.domain.dto.ProductDTO;
import com.victorxavier.product_catalog.domain.dto.CategoryDTO;
import com.victorxavier.product_catalog.domain.entity.Category;
import com.victorxavier.product_catalog.domain.entity.Product;
import com.victorxavier.product_catalog.domain.entity.ProductPage;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.PageRequest;
import com.victorxavier.product_catalog.domain.repository.ProductRepository;
import com.victorxavier.product_catalog.domain.repository.CategoryRepository;
import com.victorxavier.product_catalog.domain.usecase.product.*;
import com.victorxavier.product_catalog.domain.exception.ResourceNotFoundException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ProductServiceImpl implements FindProductUseCase, CreateProductUsecase, UpdateProductUseCase, DeleteProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO findById(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapToDTO(entity);
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product entity = mapToEntity(productDTO);
        entity.setDate(Instant.now());
        entity = productRepository.save(entity);
        return mapToDTO(entity);
    }
    @Override
    public void delete(Long id) {
        Optional<Product> obj = productRepository.findById(id);
        if (obj.isEmpty()) {
            throw new ResourceNotFoundException("Entity not found");
        }
        productRepository.deleteById(id);
    }
    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        entity.setName(productDTO.name());
        entity.setDescription(productDTO.description());
        entity.setPrice(productDTO.price());
        entity.setImgUrl(productDTO.imgUrl());

        // Update categories
        entity.getCategories().clear();
        if (productDTO.categories() != null) {
            for (CategoryDTO catDTO : productDTO.categories()) {
                Optional<Category> cat = categoryRepository.findById(catDTO.id());
                Category category = cat.orElseThrow(() -> new ResourceNotFoundException("Category not found: " + catDTO.id()));
                entity.getCategories().add(category);
            }
        }

        entity = productRepository.save(entity);
        return mapToDTO(entity);
    }
    @Override
    public Page<ProductDTO> findAllPaged(String categoryId, String name, PageRequest pageRequest) {
        List<Long> categoryIds = new ArrayList<>();
        if (!"0".equals(categoryId)) {
            categoryIds = Arrays.asList(categoryId.split(",")).stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }

        ProductPage page = productRepository.searchProducts(
                categoryIds,
                name.trim(),
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                pageRequest.getSortField()
        );

        List<Long> productIds = page.getContent().stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());

        List<Product> entities = productRepository.findProductsWithCategories(productIds);
        List<ProductDTO> dtos = entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new Page<>(
                dtos,
                page.getPageNumber(),
                page.getPageSize(),
                page.getTotalElements()
        );
    }

    private Product mapToEntity(ProductDTO dto) {
        Product entity = new Product();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        entity.setImgUrl(dto.imgUrl());
        entity.setDate(dto.date());

        if (dto.categories() != null) {
            for (CategoryDTO categoryDTO : dto.categories()) {
                Optional<Category> cat = categoryRepository.findById(categoryDTO.id());
                Category category = cat.orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryDTO.id()));
                entity.getCategories().add(category);
            }
        }
        return entity;
    }

    private ProductDTO mapToDTO(Product entity) {
        List<CategoryDTO> categories = new ArrayList<>();
        if (entity.getCategories() != null) {
            categories = entity.getCategories().stream()
                    .map(category -> new CategoryDTO(category.getId(), category.getName()))
                    .collect(Collectors.toList());
        }

        return new ProductDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl(),
                entity.getDate(),
                categories
        );
    }
}
