package com.it_chhean.ecommerce.identity.infrastructure.persistence.impl;

import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.domain.repository.RoleRepository;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.SpringDataRoleRepository;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.RoleJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final SpringDataRoleRepository springDataRoleRepository;

    public RoleRepositoryImpl(SpringDataRoleRepository springDataRoleRepository) {
        this.springDataRoleRepository = springDataRoleRepository;
    }

    @Override
    public RoleResponse save(Role role) {
        RoleJpaEntity entity = RoleJpaEntity.fromDomain(role);
        RoleJpaEntity savedEntity = springDataRoleRepository.save(entity);
        return RoleResponse.from(savedEntity.toDomain());
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return springDataRoleRepository.findById(id)
                .map(RoleJpaEntity::toDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return springDataRoleRepository.findByName(name)
                .map(RoleJpaEntity::toDomain);
    }

    @Override
    public boolean isRoleAlreadyInUse(String role) {
        return springDataRoleRepository.existsByName(role);
    }

    @Override
    public List<Role> findAll() {
        List<RoleJpaEntity> entities = springDataRoleRepository.findAll();
        return entities.stream()
                .map(entity -> new Role(entity.getId(), entity.getName()))
                .toList();
    }

    @Override
    public boolean existsById(Integer id) {
        return springDataRoleRepository.existsById(id);
    }

    @Override
    public void delateById(Integer id) {
        springDataRoleRepository.deleteById(id);
    }
}
