package com.haruhanip.domains.token.repository;

public interface RefreshTokenRepository {

    public void save(String userId, String refreshToken, long ttl);

    Long findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
