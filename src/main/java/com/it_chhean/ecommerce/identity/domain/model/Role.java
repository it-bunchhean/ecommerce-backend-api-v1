package com.it_chhean.ecommerce.identity.domain.model;

import java.util.Objects;

public class Role {

    private final RoleId id;
    private String name;

    private Role(RoleId id, String name) {
        this.id = Objects.requireNonNull(id);
        this.setName(name);
    }

    public static Role reconstitute(RoleId id, String name) {
        return new Role(id, name);
    }

    public RoleId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Safe update with validatioin rules
    public void rename(String newName) {
        this.setName(newName);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("Role name connot be empty");
        }

        // Validate prefix for role name
        if (!name.startsWith("ROLE_")) {
            throw new IllegalArgumentException("Role name must begin with ROLE_ prefix");
        }

        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
