package com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.domain.model.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
public class UserJpaEntity {

    @Id
    private Long id;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id") 
    )
    private Set<RoleJpaEntity> roles = new HashSet<>();

    // required by Jpa spec
    protected UserJpaEntity() {}

    
    // Bidirectional Conversion methods
    public static UserJpaEntity fromDomain(com.it_chhean.ecommerce.identity.domain.model.User user) {
        if (user == null) return null;

        UserJpaEntity entity = new UserJpaEntity();
        entity.email = user.getEmail();
        entity.passwordHash = user.getPasswordHash();
        entity.status = user.getStatus().name();
        entity.createdAt = user.getCreatedAt();

        if (user.getProfile() != null) {
            entity.setProfile(UserProfileJpaEntity.fromDomain(user.getProfile()));
        }

        entity.roles = user.getRoles().stream()
            .map(RoleJpaEntity::fromDomain)
            .collect(Collectors.toSet());

        return entity;
    }


    public com.it_chhean.ecommerce.identity.domain.model.User toDomain() {
        Set<Role> domainRoles = this.roles.stream()
                .map(RoleJpaEntity::toDomain)
                .collect(Collectors.toSet());

        com.it_chhean.ecommerce.identity.domain.model.UserProfile domainProfile =
                this.profile != null ? this.profile.toDomain() : null;

        return com.it_chhean.ecommerce.identity.domain.model.User.reconstitute(
                this.id,
                this.email,
                this.passwordHash,
                domainProfile,
                UserStatus.valueOf(this.status),
                this.createdAt,
                domainRoles
        );
    }

}
