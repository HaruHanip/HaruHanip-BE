package com.haruhanip.domains.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Entity
public class OAuthIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_identity_id")
    private Long oauthIdentityId;

    @Column(nullable = false, unique = true)
    private String sub;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static OAuthIdentity of(String sub, OAuthProvider provider, User user) {
        return OAuthIdentity.builder()
                .sub(sub)
                .provider(provider)
                .user(user)
                .build();
    }
}