package com.victorxavier.product_catalog.infrastructure.persistence.impl.product;

import com.victorxavier.product_catalog.domain.entity.Product;
import com.victorxavier.product_catalog.domain.entity.ProductPage;
import com.victorxavier.product_catalog.domain.projection.ProductProjection;
import com.victorxavier.product_catalog.domain.repository.ProductRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.jpa.ProductJpaRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.mapper.product.ProductEntityMapper;
import com.victorxavier.product_catalog.infrastructure.persistence.mapper.product.ProductProjectionMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.victorxavier.product_catalog.infrastructure.persistence.adapter.PageableAdapter;
import com.victorxavier.product_catalog.infrastructure.persistence.adapter.SortAdapter;
import com.victorxavier.product_catalog.domain.pagination.Pageable;
import com.victorxavier.product_catalog.domain.pagination.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductEntityMapper productMapper;
    private final ProductProjectionMapper projectionMapper;

    @Autowired
    public ProductRepositoryImpl(
            ProductJpaRepository productJpaRepository,
            ProductEntityMapper productMapper,
            ProductProjectionMapper projectionMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productMapper = productMapper;
        this.projectionMapper = projectionMapper;
    }

    @Override
    public Product save(Product product) {
        var entity = productMapper.toEntity(product);
        var savedEntity = productJpaRepository.save(entity);
        return productMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductPage searchProducts(List<Long> categoryIds, String name, int page, int size, String sortField, String direction) {
        Sort.Direction domainDirection = 
            "DESC".equalsIgnoreCase(direction) ? 
                Sort.Direction.DESC : 
                Sort.Direction.ASC;
        
        Pageable domainPageable = 
            new Pageable(page, size, sortField, domainDirection);
        
        org.springframework.data.domain.Pageable springPageable = PageableAdapter.toSpring(domainPageable);

        org.springframework.data.domain.Page<ProductProjection> result = productJpaRepository.searchProducts(categoryIds, name, springPageable)
                .map(projectionMapper::toDomain);

        return new ProductPage(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findProductsWithCategories(List<Long> productIds) {
        return productJpaRepository.searchProductsWithCategories(productIds).stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());
    }
}