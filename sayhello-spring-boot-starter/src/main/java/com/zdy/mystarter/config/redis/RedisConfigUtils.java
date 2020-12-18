package com.zdy.mystarter.config.redis;

import ch.qos.logback.core.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * TODO 装配Redis
 * Created by Jason on 2020/1/13.
 * todo 装配Redis到SpringBooot中的流程：
 * 第一步：在pom.xml中添加redis的依赖支持。
 * 第二步：application.properties中添加配置参数。
 * 第三步：书写Redis工具类。
 * 第四步：在需要缓存的地方，获取redis工具类，调用存取等操作方法。
 * todo 重要说明:
 *  Redis数据类型:
 *      String
 *      map
 *
 *
 */
//@Component
public class RedisConfigUtils {

    Logger logger= LoggerFactory.getLogger(RedisConfigUtils.class);
    /**
     * todo 自动装配redis操作模版对象
     */
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * todo redis工具的构造器，@Comonent加载的时候会用到
     * @param redisTemplate
     */
    public RedisConfigUtils(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    /**
     * todo 设置指定key的缓存失效时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key,long time){
        try{
            if(time>0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            logger.error("redis exception  exprie  fail, message={}"+e.getMessage());
            return false;
        }
    }

    /**
     * 获取指定可以的失效时间
     * @param key
     * @return
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * todo 判断指定的key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        try{
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            logger.error("redis exception  hasKey  fail, message={}"+e.getMessage());
            return false;
        }
    }

    /**
     * todo 删除指定的key的缓存数据
     * @param keys 可以传入一个或者多个
     */
    public void del(String... keys){
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }


    //TODO ============================String=============================
    /**
     * todo 普通缓存-取出
     * @param key       键
     * @return          值
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * todo 普通缓存-存入
     * @param key       键
     * @param value     值
     * @return
     */
    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            logger.error("redis exception set-2 fail, message={}"+e.getMessage());
            return false;
        }
    }

    /**
     * todo 普通缓存放入并设置时间
     * @param key       键
     * @param value     值
     * @param expire    时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return
     */
    public boolean set(String key,Object value,long expire){
        try {
            if (expire > 0) {
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("redis exception set-3 fail, message={}"+e.getMessage());
            return false;
        }
    }

    /**
     * todo 递增
     * @param key       键
     * @param delta     递增数值
     * @return
     */
    public long incr(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * todo 递减
     * @param key       键
     * @param delta     递减数值
     * @return
     */
    public long decr(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key,delta);
    }

    //TODO ============================Map=============================

    /**
     * todo HashGet
     * @param key   键 不能为null
     * @param item  项 不能为null
     * @return
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     * todo 获取hashKey对应的所有键值
     * @param key  键
     * @return  对应的多个键值
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * todo HashSet  一次性设置多个值： key:values=1:n
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key,Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * todo HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }











}
