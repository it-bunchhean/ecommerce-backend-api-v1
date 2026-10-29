package com.it_chhean.ecommerce.identity.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Role {

    private final Integer id;
    private String name;

    // Private constructor enforcing core domain constraints
    private Role(Integer id, String name) {
        this.id = id;
        this.setName(name);
    }

    /**
     * Domain Factory Methods
     */

    // Factory method for provisioning a brand-new role
    public static Role create(String name) {
        return new Role(null, name);
    }

    // Factory method used strictly by data mappers when rebuilding state from db
    public static Role reconstitute(Integer id, String name) {
        Objects.requireNonNull(id, "Role ID cannot be null during reconstitute");
        return new Role(id, name);
    }

    /**
     * Core Domain Logic & Business Rules
     */

    // Safe update with validation rules
    public void rename(String newName) {
        this.setName(newName);
    }

    private void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be empty");
        }

        // Validate standard security naming convention requirements
        if (!name.startsWith("ROLE_")) {
            throw new IllegalArgumentException("Role name must begin with 'ROLE_' prefix");
        }

        this.name = name;
    }

    /**
     * Getters
     */
    public Integer getId() { return id; }
    public String getName() { return name; }

    /**
     * Identity Equality Protocols
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Role role)) {
            return false;
        }

        return id != null && id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
