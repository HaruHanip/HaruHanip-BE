package com.haruhanip.security;

import com.haruhanip.domains.user.domain.OAuthProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User extends DefaultOAuth2User implements CustomUser{
    private final OAuthProvider provider;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String,Object> attributes,
                            String nameAttributeKey,
                            OAuthProvider provider) {
        super(authorities, attributes, nameAttributeKey);
        this.provider = provider;
    }

    public OAuthProvider getProvider() {
        return provider;
    }

    @Override
    public String getSubject() {
        return getName();
    }
}

