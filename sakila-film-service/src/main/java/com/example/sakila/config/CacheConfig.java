package com.example.sakila.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

  private static final Duration TTL = Duration.ofMinutes(5L);

  @Bean
  @Primary
  public RedisCacheConfiguration cacheConfiguration(ObjectMapper objectMapper) {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(TTL)
        .disableCachingNullValues()
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new StringRedisSerializer()
            )
        )
        .serializeValuesWith(RedisSerializationContext.SerializationPair
            .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
  }

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(
      RedisCacheConfiguration redisCacheConfiguration, List<CachingService> cachingServices) {
    return (builder) ->
        cachingServices.forEach(service ->
            builder
                .withCacheConfiguration(service.getCacheKey(), redisCacheConfiguration));
  }
}
