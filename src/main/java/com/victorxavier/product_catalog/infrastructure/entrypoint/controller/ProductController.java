package com.victorxavier.product_catalog.infrastructure.entrypoint.controller;

import com.victorxavier.product_catalog.domain.dto.ProductDTO;
import com.victorxavier.product_catalog.domain.pagination.Page;
import com.victorxavier.product_catalog.domain.pagination.PageRequest;
import com.victorxavier.product_catalog.application.usecase.impl.product.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {

        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAllPaged(
            @RequestParam(value = "categoryId", defaultValue = "0") String categoryId,
            @RequestParam(value = "name", defaultValue = "") String name,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        PageRequest pageRequest = convertToPageRequest(pageable);
        Page<ProductDTO> pageResult = productService.findAllPaged(categoryId, name, pageRequest);
        return ResponseEntity.ok().body(pageResult);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok().body(productDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.create(productDTO);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProduct.id()).toUri();
        return ResponseEntity.created(uri).body(createdProduct);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.update(id, productDTO);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private PageRequest convertToPageRequest(Pageable pageable) {
        String sortField = "name"; // default
        String sortDirection = "ASC"; // default
        
        if (pageable.getSort().isSorted()) {
            org.springframework.data.domain.Sort.Order order = pageable.getSort().iterator().next();
            sortField = order.getProperty();
            sortDirection = order.getDirection().name();
        }
        
        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sortField, sortDirection);
    }
}