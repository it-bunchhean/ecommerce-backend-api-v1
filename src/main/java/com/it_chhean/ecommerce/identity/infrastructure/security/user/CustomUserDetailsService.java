package com.it_chhean.ecommerce.identity.infrastructure.security.user;

import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User domainUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));

        return new CustomUserDetails(domainUser);
    }

}