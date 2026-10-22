package com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_users")
public class UserJpaEntity {

    @Id
    private UUID id;

    private String email;

    private String passwordHash;
    
    private String status;

    private Instant createdAt;

    // required by Jpa spec
    protected UserJpaEntity() {}

    
    // Bidirectional Conversion methods
    public static UserJpaEntity fromDomain(com.it_chhean.ecommerce.identity.domain.model.User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.id = user.getId().value();
        entity.email = user.getEmail();
        entity.passwordHash = user.getPasswordHash();
        entity.status = user.getStatus().name();
        entity.createdAt = user.getCreatedAt();
        return entity;
    }

    public com.it_chhean.ecommerce.identity.domain.model.User toDomain() {
        return com.it_chhean.ecommerce.identity.domain.model.User.reconstitute(
                new com.it_chhean.ecommerce.identity.domain.model.UserId(this.id),
                this.email, 
                this.passwordHash,
                com.it_chhean.ecommerce.identity.domain.model.UserStatus.valueOf(this.status),
                this.createdAt
        );
    }

}
