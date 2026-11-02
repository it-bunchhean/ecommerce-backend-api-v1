package com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
@ToString
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 250)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private UserProfileJpaEntity profile;

    private String status;

    @Column(name = "create_at")
    private Instant createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id") 
    )
    private Set<RoleJpaEntity> roles = new HashSet<>();

    // required by Jpa spec
    protected UserJpaEntity() {}
    
    // Bidirectional Conversion methods
    public static UserJpaEntity fromDomain(com.it_chhean.ecommerce.identity.domain.model.User domainUser) {
        if (domainUser == null) return null;

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(domainUser.getId());
        entity.username = domainUser.getUsername();
        entity.email = domainUser.getEmail();
        entity.passwordHash = domainUser.getPasswordHash();
        entity.status = domainUser.getStatus().name();
        entity.createdAt = domainUser.getCreatedAt();

        entity.profile = domainUser.getProfile() != null 
            ? UserProfileJpaEntity.fromDomain(domainUser.getProfile()) : null;

        if (domainUser.getRoles() != null) {
            entity.roles = domainUser.getRoles().stream()
                .map(RoleJpaEntity::fromDomain)
                .collect(Collectors.toSet());
        }

        return entity;
    }

    public com.it_chhean.ecommerce.identity.domain.model.User toDomain() {
        Set domainRoles = this.roles.stream()
                .map(RoleJpaEntity::toDomain)
                .collect(Collectors.toSet());

        com.it_chhean.ecommerce.identity.domain.model.UserProfile domainProfile =
                this.profile != null ? this.profile.toDomain() : null;

        return com.it_chhean.ecommerce.identity.domain.model.User.reconstitute(
                this.id,
                this.username,
                this.email,
                this.passwordHash,
                domainProfile,
                com.it_chhean.ecommerce.identity.domain.model.UserStatus.valueOf(this.status),
                this.createdAt,
                domainRoles
        );
    }

}
