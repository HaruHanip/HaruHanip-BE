package com.haruhanip.domains.user.repository;

import com.haruhanip.domains.user.domain.OAuthIdentity;
import com.haruhanip.domains.user.domain.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OAuthIdentityRepository extends JpaRepository<OAuthIdentity, Long>{
    @Query("select i from OAuthIdentity i join fetch i.user "
            + "where i.sub = :sub and i.provider = :provider")
    Optional<OAuthIdentity> findBySubAndProvider(
            @Param("sub") String sub,
            @Param("provider") OAuthProvider provider);
}
