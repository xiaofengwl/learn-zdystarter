package com.zdy.mystarter.controller;

import com.zdy.mystarter.caches.redis.RedisUtils;
import com.zdy.mystarter.vo.Result;
import com.zdy.mystarter.vo.redis.DataInfomation;
import com.zdy.mystarter.vo.redis.RedisInputVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * TODO Redis测试
 * <pre>
 *     Redis数据类型：
 *     1）String  适合存放普通键值对
 *     2）Hash 适合存放对象类型
 *     3）List  Redis列表是简单的字符串列表，按照插入顺序排序。
 *              疑问：如果存放的是对象类型的元素，该如何根据对象的指定属性做排序呢？
 *     4）Set  无序集合
 *
 *     5）ZSet 有序集合
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/22 14:12
 * @desc
 */
@Controller
@RequestMapping("/redis")
public class RedisMainController {

    private static final long EXPIRE_TIME=20L;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    @Qualifier(value="jedisCluster")
    private JedisCluster jedisCluster;


    //写入Redis数据测试
    @RequestMapping("/1")
    @ResponseBody
    public Result test1(@RequestBody @Validated RedisInputVO inputVo){
         Result<Object> result=new Result<>();
         switch (inputVo.getOperType()){
             case "0":  //写入字符串-String
                 wirteAndReadString2Redis(inputVo,result,EXPIRE_TIME);
                 break;
             case "1":  //写入实体对象-Hash
                 wirteAndReadObject2Redis(inputVo,result,EXPIRE_TIME);
                 break;
             case "2":  //写入简单数字的加减法处理
                 wirteAndReadObject2RedisDeal(inputVo,result,EXPIRE_TIME);
                 break;
             case "3":  //写入List列表
                 wirteAndReadList2Redis(inputVo,result,EXPIRE_TIME);
                 break;
             case "4":  //写入List列表，读取排序
                 wirteAndReadList2RedisDealSort(inputVo,result,EXPIRE_TIME);
                 break;
             case "5":  //写入List列表，分页查询
                 wirteAndReadList2RedisDealPage(inputVo,result,EXPIRE_TIME);
                 break;
             case "6":  //写入Set无序集合中，（不支持分页）
                 wirteAndReadSet2Redis(inputVo,result,EXPIRE_TIME);
                 break;
             case "7":  //写入Set无序集合中，不支持分页，读取默认有排序，   问题：如何控制排序？
                 wirteAndReadSet2RedisDealSort(inputVo,result,EXPIRE_TIME);
                 break;
             case "8":  //写入ZSet有序集合中,支持分页读取，无法排序，  问题：如何控制排序？
                 wirteAndReadZSet2Redis(inputVo,result,EXPIRE_TIME);
                 break;
             case "9":  //redis集群测试
                 redisClusterTest(inputVo,result,EXPIRE_TIME);
                 break;
             default:
                 break;
         }
         return result;
    }
    //redis集群测试
    private void redisClusterTest(RedisInputVO inputVo, Result<Object> result, long expireTime) {

        System.out.println("读取已存在的key-value:name");
        System.out.println(jedisCluster.get("name"));
        System.out.println("写入：myName-xfwly");
        jedisCluster.set("myName","xfwly");

    }

    //8-写入ZSet有序集合中-分页查询OK
    private void wirteAndReadZSet2Redis(RedisInputVO inputVo, Result<Object> result, long expireTime) {
        String key = "dataZSet";
        if (redisUtils.exists(key)) {
            Set<Object> set=redisUtils.range(key,inputVo.getBeginPos(),inputVo.getEndPos());
            result.setData(set);
            redisUtils.remove(key);
        } else {
            result.setData("没有命中缓存,重新写入！");
            inputVo.getDataList().stream().forEach(each->{
                redisUtils.zAdd(key,each,1);
                redisUtils.zAdd("dataPageZSetBak",each,1);
            });
        }
    }

    //7-写入Set无序集合中，读取排序
    private void wirteAndReadSet2RedisDealSort(RedisInputVO inputVo, Result<Object> result, long expireTime) {
        String key = "dataSet";
        if (redisUtils.exists(key)) {
            Set<Object> set=redisUtils.setMembers(key);
            result.setData(set);;
            redisUtils.remove(key);
        } else {
            result.setData("没有命中缓存,重新写入！");
            //
            inputVo.getDataList().stream().forEach(each->{
                redisUtils.add(key,each);
                redisUtils.add("dataPageSetBak",each);
            });
        }
    }

    //6-写入Set无序集合中,自动排序了，卧槽
    private void wirteAndReadSet2Redis(RedisInputVO inputVo, Result<Object> result, long expireTime) {
        String key = "dataSet";
        if (redisUtils.exists(key)) {
            Set<Object> set=redisUtils.setMembers(key);
            result.setData(set);
            redisUtils.remove(key);
        } else {
            result.setData("没有命中缓存,重新写入！");
            //
            inputVo.getDataList().stream().forEach(each->{
                redisUtils.add(key,each);
                redisUtils.add("dataPageSetBak",each);
            });
        }
    }

    //5-写入List集合，分页查询-OK
    private void wirteAndReadList2RedisDealPage(RedisInputVO inputVo, Result<Object> result, long expireTime) {
        String key = "dataPageList";
        if (redisUtils.exists(key)) {
            List<Object> list=redisUtils.lRange(key,inputVo.getBeginPos(),inputVo.getEndPos());
            result.setData(list);
        } else {
            result.setData("没有命中缓存,重新写入！");
            //
            inputVo.getDataList().stream().forEach(each->{
                redisUtils.lPush(key,each);
                redisUtils.lPush("dataPageListBak",each);
            });
        }
    }

    //4-写入集合，读取排序: todo 验证List<实体类型>的list集合排序失败
    private void wirteAndReadList2RedisDealSort(RedisInputVO inputVo, Result<Object> result, long expireTime) {
        String key = "dataList";
        if (redisUtils.exists(key)) {
            //
            SortParameters.Order order=SortParameters.Order.DESC;
            List<Object> list=redisUtils.lRangeOnSort(key,0,-1,inputVo.getSortField(),order);
            result.setData(list);
        } else {
            result.setData("没有命中缓存,重新写入！");
            //设置集合
            inputVo.getDataList().stream().forEach(each->{
                redisUtils.lPush(key,each);
                redisUtils.lPush("dataListBak",each);
            });

        }
    }

    //3-写入集合
    private void wirteAndReadList2Redis(RedisInputVO inputVo, Result<Object> result, long expireTime) {
        String key = "dataList";
        if (redisUtils.exists(key)) {
            List<Object> list=redisUtils.lRange(key,0,2);
            result.setData(list);
        } else {
            result.setData("没有命中缓存,重新写入！");
            //设置集合
            redisUtils.lPush(key,inputVo.getDataList());
            redisUtils.lPush("dataListBak",inputVo.getDataList());
        }
    }

    //2-写入简单数字的加减法处理
    private void wirteAndReadObject2RedisDeal(RedisInputVO inputVo, Result<Object> result, long expireTime) {
        String key="age";
        if(redisUtils.exists(key)){
            int beforeValue= (int) redisUtils.get(key);
            //递增
            redisUtils.incrementForValue(key,1);
            int lastValue=(int) redisUtils.get(key);
            result.setData("前："+beforeValue+",后："+lastValue);
        }else{
            result.setData("没有命中缓存,重新写入！");
            //设置单个String数据
            redisUtils.set(key,inputVo.getAge(),expireTime, TimeUnit.SECONDS);
            redisUtils.set("agebak",inputVo.getAge());
        }

    }

    //1-写入实体对象-HASH
    private void wirteAndReadObject2Redis(RedisInputVO inputVo, Result<Object> result,long expireTime) {
        String key="data";
        if(redisUtils.exists(key)){
            RedisInputVO data=new RedisInputVO();
            String name= (String) redisUtils.hmGet(key,"data.name");
            Integer age= (Integer)  redisUtils.hmGet(key,"data.age");
            String address= (String)   redisUtils.hmGet(key,"data.address");
            List<DataInfomation> dataList= (List<DataInfomation>) redisUtils.hmGet(key,"data.dataList");
            data.setAddress(address);
            data.setName(name);
            data.setAge(age);
            data.setDataList(dataList);
            result.setData(data);
        }else{
            result.setData("没有命中缓存,重新写入！");
            //设置实体对象数据
            redisUtils.hmSet(key,"data.name",inputVo.getName());
            redisUtils.hmSet(key,"data.age",inputVo.getAge());
            redisUtils.hmSet(key,"data.address",inputVo.getAddress());
            redisUtils.hmSet(key,"data.dataList",inputVo.getDataList());
        }

    }

    //0-写入字符串
    private void wirteAndReadString2Redis(RedisInputVO inputVo, Result<Object> result,long expireTime) {
        String key="name";
        if(redisUtils.exists(key)){
            String value= (String) redisUtils.get(key);
            result.setData(value);
        }else{
            result.setData("没有命中缓存,重新写入！");
            //设置单个String数据
            redisUtils.set(key,"xfwl-lvjun",expireTime, TimeUnit.SECONDS);
            redisUtils.set("namebak","xfwl-lvjun");
        }
    }

}
