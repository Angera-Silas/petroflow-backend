package com.angerasilas.petroflow_backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import redis.clients.jedis.UnifiedJedis;

@Component
@ConditionalOnProperty(name = "app.redis.jedis.test-on-startup", havingValue = "true")
public class ConnectBasicExample implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ConnectBasicExample.class);
    private final UnifiedJedis jedis;

    public ConnectBasicExample(UnifiedJedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            String res1 = jedis.set("foo", "bar");
            String res2 = jedis.get("foo");
            log.info("Jedis set result: {}", res1);
            log.info("Jedis get result: {}", res2);
        } catch (Exception e) {
            log.error("Jedis connectivity check failed: {}", e.getMessage());
            throw e;
        }
    }
}
