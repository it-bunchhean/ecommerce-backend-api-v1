package com.it_chhean.ecommerce.identity.domain.model;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        Objects.requireNonNull(value, "User ID value cannot be null");
    }
}
