package com.it_chhean.ecommerce.identity.application.mapper;

import org.springframework.stereotype.Component;

import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.UserJpaEntity;

@Component
public class UserMapper {

    public User toDomain(UesrJpaEntity jpa) {
        if (jpa == null) return null; 

        UserProfile domainProfile = null;
        if (jpa.)
    }
}
