package org.example.bankup.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory, SimpleControllerHandlerAdapter simpleControllerHandlerAdapter) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class)));
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
