package br.com.iteris.caiqueborges.testcontainersdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    public static final String FEE_PERCENTAGE_DISCOUNT_KEY = "fee-discount";

    @Bean
    public StringRedisTemplate getRedisTemplateStringBigDecimal(RedisConnectionFactory connectionFactory) {

        final var redisTemplate = new StringRedisTemplate(connectionFactory);

        redisTemplate.opsForValue().set(FEE_PERCENTAGE_DISCOUNT_KEY, "0.05");

        return redisTemplate;

    }

}
