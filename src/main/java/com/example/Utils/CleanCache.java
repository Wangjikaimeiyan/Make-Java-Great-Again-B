package com.example.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CleanCache {
    @Autowired
    private RedisTemplate redisTemplate;
    public void cleanCache(String key){
        log.info("开始清理缓存：{}", key);
        redisTemplate.delete(key);
    }
}
