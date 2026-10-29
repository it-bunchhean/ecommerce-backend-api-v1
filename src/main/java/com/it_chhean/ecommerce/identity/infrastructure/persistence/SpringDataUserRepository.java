package com.it_chhean.ecommerce.identity.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.UserJpaEntity;

/**
 * Standard spring data layer working behind the scence.
 */
public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByEmail(String email);

    List<UserJpaEntity> id(UUID id);
}

