package com.haroot.home_page.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
  private final RedisTemplate<String, Object> redisTemplate;

  public void set(String key, Object val) {
    redisTemplate.opsForValue().set(key, val);
  }

  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }
}
