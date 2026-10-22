package com.it_chhean.ecommerce.identity.domain.repository;

import java.util.Optional;

import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.model.UserId;

/**
 * Domain boundary definition. The domain defines what operations it requires.
 */
public interface UserRepository {
    void save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(String email);
}
