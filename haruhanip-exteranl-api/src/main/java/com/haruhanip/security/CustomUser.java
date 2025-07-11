package com.haruhanip.security;

import com.haruhanip.domains.user.domain.OAuthProvider;

public interface CustomUser {
    public OAuthProvider getProvider();
    public String getSubject();
}
