package com.angerasilas.petroflow_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.UnifiedJedis;

@Configuration
@ConditionalOnProperty(name = "app.redis.jedis.enabled", havingValue = "true")
public class JedisConfig {

    @Bean(destroyMethod = "close")
    public UnifiedJedis unifiedJedis(
            @Value("${app.redis.jedis.host}") String host,
            @Value("${app.redis.jedis.port}") int port,
            @Value("${app.redis.jedis.username:default}") String username,
            @Value("${app.redis.jedis.password}") String password,
            @Value("${app.redis.jedis.ssl:false}") boolean sslEnabled) {

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .user(username)
                .password(password)
                .ssl(sslEnabled)
                .build();

        return new UnifiedJedis(new HostAndPort(host, port), config);
    }
}
