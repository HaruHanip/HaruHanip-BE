package com.haruhanip.security;

import com.haruhanip.domains.user.domain.OAuthProvider;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;


public class CustomOidcUser extends DefaultOidcUser implements CustomUser {
    private final OAuthProvider provider;

    public CustomOidcUser(OidcUser delegate, OAuthProvider provider) {
        super(delegate.getAuthorities(), delegate.getIdToken(), delegate.getUserInfo());
        this.provider = provider;
    }

    @Override
    public OAuthProvider getProvider() {
        return provider;
    }

    @Override
    public String getSubject() {
        return getIdToken().getSubject();
    }
}