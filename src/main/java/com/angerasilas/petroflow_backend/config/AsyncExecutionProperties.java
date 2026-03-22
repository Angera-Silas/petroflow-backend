package com.angerasilas.petroflow_backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.async")
public class AsyncExecutionProperties {

    private ExecutorPool io = new ExecutorPool(8, 16, 500, "pf-io-");
    private ExecutorPool report = new ExecutorPool(4, 8, 200, "pf-report-");
    private ExecutorPool notification = new ExecutorPool(4, 8, 500, "pf-notify-");
    private ExecutorPool sync = new ExecutorPool(4, 8, 300, "pf-sync-");

    @Getter
    @Setter
    public static class ExecutorPool {
        private int corePoolSize;
        private int maxPoolSize;
        private int queueCapacity;
        private String threadNamePrefix;

        public ExecutorPool() {
        }

        public ExecutorPool(int corePoolSize, int maxPoolSize, int queueCapacity, String threadNamePrefix) {
            this.corePoolSize = corePoolSize;
            this.maxPoolSize = maxPoolSize;
            this.queueCapacity = queueCapacity;
            this.threadNamePrefix = threadNamePrefix;
        }
    }
}
