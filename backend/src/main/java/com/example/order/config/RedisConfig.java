package com.example.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * Redis 配置
 *
 * @author liuxinsi
 * @date 2026-06-08
 */
@Configuration
public class RedisConfig {

    /**
     * Redis 消息监听容器
     *
     * @param factory Redis 连接工厂
     * @return 监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        return container;
    }
}
