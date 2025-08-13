package com.victorxavier.product_catalog.domain.repository;

import com.victorxavier.product_catalog.domain.entity.Product;
import com.victorxavier.product_catalog.domain.entity.ProductPage;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void delete(Product product);

    ProductPage searchProducts(List<Long> categoryIds, String name, int page, int size, String sort);
    List<Product> findProductsWithCategories(List<Long> productIds);

}
