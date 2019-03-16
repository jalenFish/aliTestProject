package com.common.commonUtil.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 * Created by yjl on 2018-12-07.
 * RedisCacheConfig redis自定义的工具类，自定义redis的key生成规则
 *   RedisCacheConfig extends org.springframework.cache.annotation.CachingConfigurerSupport，自定义redis的key生成规则，
 *   如果不在注解参数中注明key=“”的话，就采用这个类中的key生成规则生成key
 *
 *   @Cacheable("a")注解的意义就是把该方法的查询结果放到redis中去，下一次再发起查询就去redis中去取，存在redis中的数据的key就是a；
 * @CacheEvict(value={"a","b"},allEntries=true) 的意思就是执行该方法后要清除redis中key名称为a,b的数据；
 *
 *
 * 先介绍几个注解

　　1》@CacheConfig  配置在类上，cacheNames即定义了本类中所有用到缓存的地方，都去找这个库。只要使用了这个注解，在方法上@Cacheable    @CachePut   @CacheEvict就可以不用写value去找具体库名了。【一般不怎么用】

　　2》@Cacheable  配置在方法或类上，作用：本方法执行后，先去缓存看有没有数据，如果没有，从数据库中查找出来，给缓存中存一份，返回结果，下次本方法执行，在缓存未过期情况下，先在缓存中查找，有的话直接返回，没有的话从数据库查找

　　3》@CachePut   类似于更新操作，即每次不管缓存中有没有结果，都从数据库查找结果，并将结果更新到缓存，并返回结果

　　4》@CacheEvict 用来清除用在本方法或者类上的缓存数据（用在哪里清除哪里）
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
    protected final static Logger log = LoggerFactory.getLogger(RedisCacheConfig.class);

    private volatile JedisConnectionFactory mJedisConnectionFactory;
    private volatile RedisTemplate<String, String> mRedisTemplate;
    private volatile RedisCacheManager mRedisCacheManager;

    public RedisCacheConfig() {
        super();
    }

    public RedisCacheConfig(JedisConnectionFactory mJedisConnectionFactory, RedisTemplate<String, String> mRedisTemplate, RedisCacheManager mRedisCacheManager) {
        super();
        this.mJedisConnectionFactory = mJedisConnectionFactory;
        this.mRedisTemplate = mRedisTemplate;
        this.mRedisCacheManager = mRedisCacheManager;
    }

    public JedisConnectionFactory redisConnectionFactory() {
        return mJedisConnectionFactory;
    }

    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        return mRedisTemplate;
    }

    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        return mRedisCacheManager;
    }



    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method,
                                   Object... params) {
                //规定  本类名+方法名+参数名 为key
                StringBuilder sb = new StringBuilder();
//                sb.append(target.getClass().getName()+"_");
//                sb.append(method.getName()+"_");
                for (Object obj : params) {
                    sb.append(obj.toString()+",");
                }
                return sb.toString();
            }
        };
    }



}
