package com.bryan.cloud.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY; // Feign默认不使用重试策略
//         初始间隔为100ms，最大重试间隔为1秒，最大重试次数为3次
//        return new Retryer.Default(100, 1, 2);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
