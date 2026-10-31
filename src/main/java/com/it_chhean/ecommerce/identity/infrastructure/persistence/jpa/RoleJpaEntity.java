package com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "tbl_roles")
@Getter
public class RoleJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    protected RoleJpaEntity() {}

    public static RoleJpaEntity fromDomain(com.it_chhean.ecommerce.identity.domain.model.Role domainRole) {
        RoleJpaEntity entity = new RoleJpaEntity();
        entity.id = domainRole.getId();
        entity.name = domainRole.getName();
        return entity;
    }

    public com.it_chhean.ecommerce.identity.domain.model.Role toDomain() {
        return com.it_chhean.ecommerce.identity.domain.model.Role.reconstitute(
                this.id,
                this.name
        );
    }
}
