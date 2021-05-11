package com.sang.redisdemo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设值
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        logger.info("set key:{} value:{}", key, value);
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设值
     * @param key
     * @param value
     * @param expireTime 过期时间，单位：s
     */
    public void set(String key, String value, int expireTime) {
        logger.info("set key:{} value:{} expireTime:{}", key, value, expireTime);
        stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 取值
     * @param key
     * @return
     */
    public String get(String key) {
        logger.info("get key:{}", key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public Boolean del(String key) {
        if(exists(key)) {
            return stringRedisTemplate.delete(key);
        }else {
            logger.error("del key:{}", key+"不存在");
            return false;
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        Boolean exists = stringRedisTemplate.hasKey(key);
        logger.info("exists key:{} hasKey:{}", key, exists);
        return exists;
    }

}
