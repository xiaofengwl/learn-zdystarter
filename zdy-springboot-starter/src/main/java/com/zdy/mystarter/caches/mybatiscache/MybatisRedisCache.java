package com.zdy.mystarter.caches.mybatiscache;

import com.zdy.mystarter.basic.awares.SpringContextHolder;
import com.zdy.mystarter.caches.redis.RedisUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * TODO 为Mybatis设置二级缓存,使用redis来实现缓存功能
 * <pre>
 *     使用注解:@CacheNameSpace(implementation=MybatisRedisCache.class,size=65535)来开启二级缓存
 *     注解是用来修饰Mapper接口
 *     todo  mybatis的MapperProxy
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/28 10:13
 * @desc
 */
public class MybatisRedisCache implements Cache {

    /**
     * redis操作Bean
     */
    private RedisUtils redisUtils;

    /**
     * 缓存的key,可以设置为Mapper的全路径，比如：com.zdy.mystarter.intergration.mapper.basic.BasicMapper
     * 可以通过这个key在redis中查询到这个key下所有的field=value对
     */
    private String key;
    /**
     * 缓存是否已初始化标记，在每次工程启动时可执行一次，保证重启工程能够正确重新加载缓存，而不是使用重启前的缓存数据
     */
    private boolean isInitCache=false;

    /**
     * 默认的超时时间，单位：s
     */
    private static final int DEFAULT_EXPIRE_TIME=60;

    public MybatisRedisCache(){
        System.out.println("com.zdy.mystarter.caches.mybatiscache.MybatisRedisCache");
    }

    public MybatisRedisCache(String key){
        if(StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("cache must has an key!");
        }
        this.key=key;
        //其他初始化操作
        this.redisUtils= SpringContextHolder.getBean(RedisUtils.class);
    }

    /**
     * 缓存的标识符
     * @return The identifier of this cache
     */
    @Override
    public String getId() {
        return this.key;
    }

    /**
     * 写入数据
     * @param field   Can be any object but usually it is a {@link CacheKey}
     * @param value The result of a select.
     */
    @Override
    public void putObject(Object field, Object value) {
        redisUtils.hmSet(this.key,field.toString(),value);
        redisUtils.expire(this.key,DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 获取数据
     * @param field The key
     * @return The object stored in the cache.
     */
    @Override
    public Object getObject(Object field) {
        if(!redisUtils.exists(this.key)){
            return null;
        }
        Object result=redisUtils.hmGet(this.key,field.toString());
        return result;
    }

    /**
     * 删除数据
     * @param field The key
     * @return Not used
     */
    @Override
    public Object removeObject(Object field) {
        return redisUtils.hmDel(this.key,field.toString());
    }

    /**
     * Clears this cache instance
     */
    @Override
    public void clear() {
        Map<String,String> all= (Map<String, String>) redisUtils.get(this.key);
        all.forEach((field,value)->{
            redisUtils.hmDel(this.key,field.toString());
        });
    }

    /**
     *
     */
    @Override
    public int getSize() {
        return redisUtils.hmGetValuesLen(this.key);
    }

    /**
     * 读写锁，在最新的jar包中，在Cache接口中已经给了default实现
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
