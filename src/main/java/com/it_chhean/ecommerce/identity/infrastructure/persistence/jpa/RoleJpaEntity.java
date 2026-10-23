package com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "tbl_roles")
@Getter
public class RoleJpaEntity {

    @Id
    private Long id;
    private String name;

    protected RoleJpaEntity() {}

    public static RoleJpaEntity fromDomain(com.it_chhean.ecommerce.identity.domain.model.Role domainRole) {
        RoleJpaEntity entity = new RoleJpaEntity();
        entity.id = domainRole.getId().value();
        entity.name = domainRole.getName();
        return entity;
    }

    public com.it_chhean.ecommerce.identity.domain.model.Role toDomain() {
        return com.it_chhean.ecommerce.identity.domain.model.Role.reconstitute(
                new com.it_chhean.ecommerce.identity.domain.model.RoleId(this.id),
                this.name
        );
    }
}
