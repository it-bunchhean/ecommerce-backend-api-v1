package com.it_chhean.ecommerce.identity.domain.model;

import java.util.Objects;

public record RoleId(Long value) {
    public RoleId {
        Objects.requireNonNull(value, "Role ID value connot be null");
    }
}
