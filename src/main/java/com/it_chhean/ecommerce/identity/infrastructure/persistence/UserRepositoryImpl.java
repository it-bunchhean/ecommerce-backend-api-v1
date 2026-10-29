package com.it_chhean.ecommerce.identity.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.repository.UserRepository;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.UserJpaEntity;

/**
 * Concrete implementation that bridge our clean domain architecture.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    public UserRepositoryImpl(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public void save(User user) {
        UserJpaEntity entity = UserJpaEntity.fromDomain(user);
        springDataUserRepository.save(entity);
    }

    @Override
    public Optional<User> findById(UserId id) {
//        return springDataUserRepository.(id)
//                .map(UserJpaEntity::toDomain);
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email)
            .map(UserJpaEntity::toDomain);
    }
}
