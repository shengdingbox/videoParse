package com.dabaotv.vip.parse.util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 周子斐 (17611555590@163.com)
 * @date 2020/3/24
 * @Description
 * @Copyright 盛鼎科技 Copyright (c)
 */
@Configuration
public class RedisConfig {

    /**
     * 实例化 RedisTemplate 对象
     * 提供给 RedisUtil 使用
     *
     * @param redisConnectionFactory springboot配置好的连接工厂
     * @return RedisTemplate
     * @autor 周子斐
     * @date 2018年5月26日 08:47:27
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式,并开启事务
     * @param redisTemplate
     * @param factory
     */
    private void initRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(factory);
    }

    /**
     * 注入封装RedisTemplate 给RedisUtil提供操作类
     *
     * @param redisTemplate
     * @return RedisUtil
     * @autor zyj
     * @date 2018年5月26日 08:47:27
     */
    @Bean(name = "redisUtil")
    public RedisUtils redisUtil(StringRedisTemplate redisTemplate) {
        RedisUtils redisUtil = new RedisUtils();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }
}

