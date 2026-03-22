package com.angerasilas.petroflow_backend.config;

import java.util.concurrent.Executor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@EnableConfigurationProperties(AsyncExecutionProperties.class)
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor(AsyncExecutionProperties properties) {
        return buildExecutor(properties.getIo());
    }

    @Bean(name = "ioExecutor")
    public Executor ioExecutor(AsyncExecutionProperties properties) {
        return buildExecutor(properties.getIo());
    }

    @Bean(name = "reportExecutor")
    public Executor reportExecutor(AsyncExecutionProperties properties) {
        return buildExecutor(properties.getReport());
    }

    @Bean(name = "notificationExecutor")
    public Executor notificationExecutor(AsyncExecutionProperties properties) {
        return buildExecutor(properties.getNotification());
    }

    @Bean(name = "syncExecutor")
    public Executor syncExecutor(AsyncExecutionProperties properties) {
        return buildExecutor(properties.getSync());
    }

    private ThreadPoolTaskExecutor buildExecutor(AsyncExecutionProperties.ExecutorPool pool) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(pool.getCorePoolSize());
        executor.setMaxPoolSize(pool.getMaxPoolSize());
        executor.setQueueCapacity(pool.getQueueCapacity());
        executor.setThreadNamePrefix(pool.getThreadNamePrefix());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }
}
