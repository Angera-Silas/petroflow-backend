package com.angerasilas.petroflow_backend.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * Redis Cache Configuration for Distributed Caching
 * Provides:
 * - Cache-aside pattern for frequently accessed data
 * - Configurable TTL based on cache type
 * - JSON serialization for cross-platform compatibility
 * - Automatic cache invalidation
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
@Slf4j
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        log.info("Initializing Redis Cache Manager");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                        .allowIfBaseType(Object.class)
                        .build(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer(objectMapper);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // Cache configuration with different TTLs
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(stringRedisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                // Short-lived cache for frequently changing data (15 min)
                .withCacheConfiguration("pendingApprovals",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(15))
                                .serializeKeysWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(stringRedisSerializer))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(jackson2JsonRedisSerializer)))
                // Medium-lived cache for semi-static data (1 hour)
                .withCacheConfiguration("users",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofHours(1))
                                .serializeKeysWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(stringRedisSerializer))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(jackson2JsonRedisSerializer)))
                // Medium-lived cache for products (6 hours)
                .withCacheConfiguration("products",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofHours(6))
                                .serializeKeysWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(stringRedisSerializer))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(jackson2JsonRedisSerializer)))
                // Long-lived cache for reference data (24 hours)
                .withCacheConfiguration("facilities",
                        RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofHours(24))
                                .serializeKeysWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(stringRedisSerializer))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair
                                        .fromSerializer(jackson2JsonRedisSerializer)))
                .build();
    }
}
