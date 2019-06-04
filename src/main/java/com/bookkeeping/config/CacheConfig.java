package com.bookkeeping.config;

import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-03 18:21 <br>
 * <p></p>
 */
@Slf4j
@EnableCaching
@Configuration
public class CacheConfig {


    /**
     * 配置 spring.cache.type = redis 时生效，设置序列化工具为GenericJackson2JsonRedisSerializer，而不是使用 jdk 的序列化
     * @param cacheProperties
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "redis", matchIfMissing = false)
    public RedisCacheConfiguration redisConfiguration(CacheProperties cacheProperties) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
                .defaultCacheConfig();
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    /**
     * 配置 spring.cache.type = generic 或者未配置 时生效，缓存使用 guava cache，而不是使用 ConcurrentMap
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "generic", matchIfMissing = true)
    public CacheManager cacheManager() {
        return new TransactionAwareCacheManagerProxy(
                new AbstractTransactionSupportingCacheManager() {
                    @Override
                    protected Collection<? extends Cache> loadCaches() {
                        return Collections.EMPTY_SET;
                    }

                    @Override
                    protected Cache getMissingCache(String name) {
                        return new AbstractValueAdaptingCache(true) {

                            private final com.google.common.cache.Cache<Object, Object> cache = CacheBuilder.newBuilder()
                                    .maximumSize(10240)
                                    .expireAfterWrite(30, TimeUnit.MINUTES)
                                    .build();

                            @Override
                            public String getName() {
                                return name;
                            }

                            @Override
                            public com.google.common.cache.Cache getNativeCache() {
                                return cache;
                            }

                            @Override
                            @SuppressWarnings("unchecked")
                            public <T> T get(Object key, Callable<T> valueLoader) {
                                ValueWrapper valueWrapper = super.get(key);
                                if (null != valueWrapper) {
                                    T t = (T) valueWrapper.get();
                                    log.debug("get {}, value {}", key, t);
                                    return t;
                                }
                                try {
                                    Object value = valueLoader.call();
                                    if (null == value) {
                                        value = isAllowNullValues() ? NullValue.INSTANCE : null;
                                    }
                                    if (!isAllowNullValues() && value == null) {

                                        throw new IllegalArgumentException(String.format(
                                                "Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.",
                                                name));
                                    }
                                    cache.put(key, toStoreValue(value));
                                    T value1 = (T) value;
                                    log.debug("get {}, value {}", key, value1);
                                    return value1;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            public void put(Object key, Object value) {
                                try {
                                    cache.put(key, toStoreValue(value));
                                    log.debug("put {}, value {}", key, value);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public ValueWrapper putIfAbsent(Object key, Object value) {
                                Object ifPresent = cache.getIfPresent(key);
                                if (null != ifPresent) {
                                    SimpleValueWrapper simpleValueWrapper = new SimpleValueWrapper(ifPresent);
                                    log.debug("putIfAbsent {}, value {}, result", key, value, simpleValueWrapper.get());
                                    return simpleValueWrapper;
                                }
                                try {
                                    cache.put(key, toStoreValue(value));
                                    log.debug("putIfAbsent {}, value {}, result", key, value, null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            public void evict(Object key) {
                                cache.invalidate(key);
                                log.debug("evict {}", key);
                            }

                            @Override
                            public void clear() {
                                cache.cleanUp();
                                log.debug("clear {}", name);
                            }

                            @Override
                            protected Object lookup(Object key) {
                                Object ifPresent = cache.getIfPresent(key);
                                log.debug("get {}, value {}", key, ifPresent);
                                return ifPresent;
                            }
                        };
                    }
                }
        );
    }
}
