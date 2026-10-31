package com.it_chhean.ecommerce.identity.infrastructure.persistence;

import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.RoleJpaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataRoleRepository extends CrudRepository<RoleJpaEntity, Integer> {
    Optional<RoleJpaEntity> findByName(String name);
    List<RoleJpaEntity> id(Integer id);
}
