package com.example.demo;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class HashRepository {

    private RedisTemplate redisTemplate;
    private HashOperations<String, String, String> hashOperations;
    private static final Long TIMEOUT = 365L;

    @Autowired
    public HashRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public boolean upload(String documentId, String key, String content) {
        try {
            log.info("Updating cache for documentId={} and key={}", documentId, key);
            hashOperations.put(documentId, key, content);
            redisTemplate.expire(documentId, TIMEOUT, TimeUnit.DAYS);
        } catch (RuntimeException e) {
            log.error("Redis upload error of documentId {} and key {}", documentId, key, e);
            return false;
        }

        return true;
    }

    public Map<String, String> get(String documentId) {
        try {
            return hashOperations.entries(documentId);
        } catch (Exception e) {
            log.error("Redis download error of documentId {}", documentId, e);
        }

        return null;
    }
}
