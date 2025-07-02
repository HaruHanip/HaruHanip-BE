package com.haruhanip.domains.token.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class BlackListService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String BLACKLIST_KEY = "at:blacklist";

    public void addToBlackList(String accessToken, Duration ttl) {
        String key = BLACKLIST_KEY + ":" + accessToken;
        redisTemplate.opsForValue().set(key, true, ttl);
    }

    public boolean isBlackListed(String accessToken) {
        String key = BLACKLIST_KEY + ":" + accessToken;
        return redisTemplate.hasKey(key);
    }
}
