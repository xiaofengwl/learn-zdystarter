package com.zdy.mystarter.caches.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisTemplate操作封装工具类
 */
@Component
public class RedisUtils {

    //@Autowired
    //@Qualifier(value="zdyJdkRedisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 设置有效时间
     * @param key
     * @param expire 时间
     * @param unit   单位
     */
    public void expire(String key,long expire,TimeUnit unit){
        redisTemplate.expire(key,expire,unit);
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 获取指定key的数据
     * @param key
     * @return
     */
    public Object hmGetAll(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }
    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    public int hmGetValuesLen(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.values(key).size();
    }

    /**
     * 哈希  删除
     * @param key
     * @param hashKey
     */
    public Object hmDel(String key,Object hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.delete(key,hashKey);
    }


    /**
     * 列表添加
     *
     * @param k  key
     * @param v  list集合对象
     */
    public void lPush(String k, Object... v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k   key
     * @param l   起始位置
     * @param l1  结束位置  -1则取全部
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 列表获取-排序-没有生效
     * TODO 总结一下：
     *      目前发现：如果List中存放的是基本数据类型，那么可排序，
     *      但是如果存放的是一些业务实体类型的的对象，则无法排序。
     * @param k   key
     * @param l   起始位置
     * @param l1  结束位置  -1则取全部
     * @param sortKey 排序的key
     * @param order 排序
     * @return
     */
    public List<Object> lRangeOnSort(String k,long l,long l1,String sortKey,SortParameters.Order order){
        //设置排序规则：
        SortQuery<String> query = SortQueryBuilder  .sort(k)      // 排序的key
                                                    .by(sortKey)        // key的正则过滤
                                                    //.noSort()         //不使用正则过滤key
                                                    .get(sortKey)     //在value里过滤正则，可以连续写多个get
                                                    .limit(l, l1)       //分页，和mysql一样
                                                    .order(order)       //正序or倒序
                                                    .alphabetical(true) //ALPHA修饰符用于对字符串进行排序，false的话只针对数字排序
                                                    .build();
        redisTemplate.sort(query);
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }



    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }
    /**
     * 集合排序获取-没有生效
     *
     * @param key
     * @return
     */
    public Set<Object> setMembersOnSort(String key) {
        //设置排序规则：
        SortQuery<String> query = SortQueryBuilder  .sort("order")      // 排序的key
                //.by(sortKey)        // key的正则过滤
                //.noSort()         //不使用正则过滤key
                //.get(sortKey)     //在value里过滤正则，可以连续写多个get
                .limit(0, 1)       //分页，和mysql一样
                .order(SortParameters.Order.DESC)       //正序or倒序
                .alphabetical(true) //ALPHA修饰符用于对字符串进行排序，false的话只针对数字排序
                .build();
        redisTemplate.sort(query);
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);

    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param begin
     * @param end
     * @return
     */
    public Set<Object> range(String key, long begin, long end) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.range(key,begin,end);
    }

    /**
     * 递增
     * @param key   第一个参数为键key
     * @param length  第二个参数为递增的值，可以为负值（表示递减）
     */
    public void incrementForValue(String key,long length){
        redisTemplate.opsForValue().increment(key,length);
    }
}