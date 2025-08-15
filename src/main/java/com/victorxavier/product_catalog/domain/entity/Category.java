package com.victorxavier.product_catalog.domain.entity;

import java.time.Instant;
import java.util.Objects;

public class Category {

    private Long id;
    private String name;
    private Instant CreatedAt;
    private Instant UpdatedAt;

    public Category() {}
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Category(Long id, String name, Instant CreatedAt, Instant UpdatedAt) {
        this.id = id;
        this.name = name;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        CreatedAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        UpdatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
