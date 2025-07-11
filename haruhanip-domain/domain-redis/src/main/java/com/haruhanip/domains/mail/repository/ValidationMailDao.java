package com.haruhanip.domains.mail.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ValidationMailDao implements ValidationMailRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY_PREFIX = "validation-mail:";


    @Override
    public void saveValidationMail(String toMail, String text, Duration duration) {
        String key = KEY_PREFIX + toMail;
        redisTemplate.opsForValue().set(key, text, duration);
    }

    @Override
    public String getValidationMail(String toMail) {
        String key = KEY_PREFIX + toMail;
        return (String) redisTemplate.opsForValue().get(key);
    }
}
