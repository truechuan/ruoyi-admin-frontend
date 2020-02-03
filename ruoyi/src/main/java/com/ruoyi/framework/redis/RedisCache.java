package com.ruoyi.framework.redis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component("redisCache")
public class RedisCache {

    @Getter
    public class StoreBean<T> {

        private long startTimeMillis;

        private long timeOutMillis;

        private T t;

        private boolean needExpired;

        public StoreBean(T t) {
            this.t = t;
            this.needExpired = false;
        }

        public StoreBean(T t, long timeOutMillis) {
            this.t = t;
            this.needExpired = true;
            this.startTimeMillis = System.currentTimeMillis();
            this.timeOutMillis = timeOutMillis;
        }


    }

    private Cache<String, StoreBean> cache = CacheBuilder.newBuilder().build();

    public <T> T set(String key, T value) {
        StoreBean bean = new StoreBean(value);
        cache.put(key, bean);
        return value;
    }

    public <T> T set(String key, T value, Integer timeout, TimeUnit timeUnit) {
        StoreBean bean = new StoreBean(value, timeUnit.toMillis(timeout));
        cache.put(key, bean);
        return value;
    }

    public <T> T get(String key) {
        StoreBean bean = cache.getIfPresent(key);
        if (bean == null) {
            return null;
        }
        if (bean.isNeedExpired()) {
            if (System.currentTimeMillis() - bean.getStartTimeMillis() > bean.getTimeOutMillis()) {
                delete(key);
                return null;
            } else {
                return (T) bean.getT();
            }
        } else {
            return (T) bean.getT();
        }
    }

    public void delete(String key) {
        cache.invalidate(key);
    }


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> T setCacheObject(String key, T value) {
        return set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public <T> T setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        return set(key, value, timeout, timeUnit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        return get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public void deleteObject(String key) {
        delete(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        Set<String> set = new HashSet<>();
        ConcurrentMap<String, StoreBean> map = cache.asMap();
        if (null != map) {
            for (String key : map.keySet()) {
                boolean isMatch = Pattern.matches(pattern, key);
                if (isMatch) {
                    set.add(key);
                }
            }
        }

        return set;
    }
}
