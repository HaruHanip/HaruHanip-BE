package com.haruhanip.domains.token.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RefreshTokenDao implements RefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REFRESH_KEY = "rt:refreshToken:";

    @Override
    public void save(String userId, String refreshToken, long ttl) {
        String key = REFRESH_KEY + ":" + refreshToken;
        redisTemplate.opsForValue().set(key, userId, Duration.ofMillis(ttl));
    }

    @Override
    public Long findByRefreshToken(String refreshToken) {
        String key = REFRESH_KEY + ":" + refreshToken;
        String userId = (String) redisTemplate.opsForValue().get(key);
        return Long.parseLong(userId);
    }

    @Override
    public void deleteByRefreshToken(String refreshToken) {
        String key = REFRESH_KEY + ":" + refreshToken;
        redisTemplate.delete(key);
    }

}
