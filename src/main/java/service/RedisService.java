package service;

import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String getFromRedis(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void put(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void removeFromRedis(String key) {
        stringRedisTemplate.delete(key);
    }
}
