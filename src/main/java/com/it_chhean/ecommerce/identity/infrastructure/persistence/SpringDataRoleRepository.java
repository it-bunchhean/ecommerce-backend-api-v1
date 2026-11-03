package com.it_chhean.ecommerce.identity.infrastructure.persistence;

import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataRoleRepository extends JpaRepository<RoleJpaEntity, Integer> {
    Optional<RoleJpaEntity> findByName(String name);
    List<RoleJpaEntity> id(Integer id);
    boolean existsByName(String roleName);
}
