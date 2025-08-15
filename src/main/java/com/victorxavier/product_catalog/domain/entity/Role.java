package com.victorxavier.product_catalog.domain.entity;

public class Role {
    private Long id;
    private String name;

    public Role() {}

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public enum Values {
        ADMIN(1L, "ADMIN"),
        BASIC(2L, "BASIC");

        private final Long roleId;
        private final String name;

        Values(Long roleId, String name) {
            this.roleId = roleId;
            this.name = name;
        }

        public Long getRoleId() {
            return roleId;
        }

        public String getName() {
            return name;
        }
    }
}