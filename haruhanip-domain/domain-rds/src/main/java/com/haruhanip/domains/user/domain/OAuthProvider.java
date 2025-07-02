package com.haruhanip.domains.user.domain;

public enum OAuthProvider {
    KAKAO("kakao"),
    NAVER("naver"),
    GOOGLE("google");

    private final String provider;

    OAuthProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
    public static OAuthProvider from(String provider) {
        for (OAuthProvider p : values()) {
            if (p.getProvider().equalsIgnoreCase(provider)) {
                return p;
            }
        }
        throw new IllegalArgumentException();
    }
}