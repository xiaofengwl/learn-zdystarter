package com.zdy.mystarter.caches.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO 配置类RedisConfig
 * </pre>
 *
 * @author lvjun
 * @version 1.0
 * @date 2020/10/22 11:09
 * @desc
 */
@Configuration
@PropertySource(value="classpath:/conf/redis.properties",ignoreResourceNotFound=false)
@RefreshScope//注解进行热部署
/**
 * 需要热加载的bean需要加上@RefreshScope注解，当配置发生变更的时候可以在不重启应用的前提下完成bean中相关属性的刷新。
 */
public class RedisConfig extends CachingConfigurerSupport {
    //获取参数配置信息
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.soTimeOut}")
    private int soTimeOut;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;
    @Value("${redis.maxRedirects:3}")
    private int maxRedirects;
    @Value("${redis.refreshTime:5}")
    private int refreshTime;
    @Value("${spring.redis.maxAttempts}")
    private int maxAttempts;

    //注入redis集群对象
    //需要说明一点：必须在配置文件中配置一台redis集群连接节点机器，保证访问，然后再按照下面的方式配置集群模式
    @Bean("jedisCluster")
    public JedisCluster getJedisCluster() {
        //redis连接池，这一块不论是任何模式，都是共有的
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(this.maxActive);         //最大连接数, 默认8个
        poolConfig.setMaxIdle(this.maxIdle);            //最大空闲连接数, 默认8个
        poolConfig.setMaxWaitMillis(this.maxWait);      //最大阻塞等待时间
        poolConfig.setMinIdle(this.minIdle);            //最小空闲连接数, 默认0
        poolConfig.setTestOnBorrow(true);               //对拿到的connection进行validateObject校验
        poolConfig.setTestOnReturn(false);              //
        poolConfig.setTestWhileIdle(true);              //

        //集群节点：
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.143.146",7010));
        nodes.add(new HostAndPort("192.168.143.146",7011));
        nodes.add(new HostAndPort("192.168.143.146",7020));
        nodes.add(new HostAndPort("192.168.143.146",7021));
        nodes.add(new HostAndPort("192.168.143.146",7030));
        nodes.add(new HostAndPort("192.168.143.146",7031));

        //此种方式，集群节点之间不设置auth密码
        //return new JedisCluster(nodes,poolConfig);

        //创建并返回redis集群对象,集群节点已设置auth密码
        return new JedisCluster(nodes,             //集群节点集合
                                timeout,           //连接超时时间
                                soTimeOut,         //读取超时时间
                                maxAttempts,       //最大尝试连接次数：
                                password,          //节点密码，虽然配置了这个密码。仍然需要在配置文件中配置：spring.redis.host等信息，保证访问
                                poolConfig);       //连接池配置
    }

    //创建Redis的Key的生成策略
    @RefreshScope
    @Bean("specilizationKeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName()+".");
                sb.append(method.getName());
                sb.append("[");
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                sb.append("]");
                return sb.toString();
            }
        };
    }

    //@Primary
    //@RefreshScope
    //@Bean("connectionFactory")
    public RedisConnectionFactory connectionFactory() {

        //redis连接池，这一块不论是任何模式，都是共有的
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(this.maxActive);
        poolConfig.setMaxIdle(this.maxIdle);
        poolConfig.setMaxWaitMillis(this.maxWait);
        poolConfig.setMinIdle(this.minIdle);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestWhileIdle(true);

        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();

        jpcb.poolConfig(poolConfig);
        JedisClientConfiguration client=jpcb.and().readTimeout(Duration.ofMillis(this.timeout)).build();

        /*******以下代码均为实测有效代码***********/

        //1、单主机模式，说明：单独的主从模式部署的redis是无法实现高可用的，--已经测试成功
        //RedisStandaloneConfiguration redisConfig=new RedisStandaloneConfiguration();
        //redisConfig.setHostName(this.host);
        //redisConfig.setPort(this.port);
        //redisConfig.setPassword(this.password);
        //redisConfig.setDatabase(0);
        //return new JedisConnectionFactory(redisConfig,client);//需要导入redis.clients包

        //2、哨兵redis--已经测试成功
        //sentinel哨兵机器
        Set<String> setRedisNode = new HashSet<>();
        setRedisNode.add("192.168.143.143:26379");
        setRedisNode.add("192.168.143.144:26379");
        setRedisNode.add("192.168.143.145:26379");
        RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration("mymaster",setRedisNode);
        redisConfig.setPassword("xfwl");//必须要设置master服务密码
        redisConfig.setDatabase(0);
        return new JedisConnectionFactory(redisConfig,poolConfig);

        ////3、集群redis-尚未测试成功
        //RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        //redisConfig.setMaxRedirects(maxRedirects);
        //
        ////支持自适应集群拓扑刷新和静态刷新源
        //ClusterTopologyRefreshOptions clusterTopologyRefreshOptions =  ClusterTopologyRefreshOptions.builder()
        //        .enablePeriodicRefresh()
        //        .enableAllAdaptiveRefreshTriggers()
        //        .refreshPeriod(Duration.ofSeconds(refreshTime))
        //        .build();
        //ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
        //        .topologyRefreshOptions(clusterTopologyRefreshOptions).build();
        //
        ////从优先，读写分离，读从可能存在不一致，最终一致性CP
        //LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
        //        .readFrom(ReadFrom.SLAVE_PREFERRED)
        //        .clientOptions(clusterClientOptions).build();
        //return new LettuceConnectionFactory(redisConfig, lettuceClientConfiguration);
    }

    /**
     * TODO  针对数据的“序列化/反序列化”，提供了多种可选择策略(RedisSerializer)
     *
     * 1）、JdkSerializationRedisSerializer：POJO对象的存取场景，使用JDK本身序列化机制，将pojo类通过ObjectInputStream/ObjectOutputStream进行序列化操作，最终redis-server中将存储字节序列。是目前最常用的序列化策略。
     *
     * 2）、StringRedisSerializer：Key或者value为字符串的场景，根据指定的charset对数据的字节序列编码成string，是“new String(bytes, charset)”和“string.getBytes(charset)”的直接封装。是最轻量级和高效的策略。
     *
     * 3）、JacksonJsonRedisSerializer：jackson-json工具提供了javabean与json之间的转换能力，可以将pojo实例序列化成json格式存储在redis中，也可以将json格式的数据转换成pojo实例。因为jackson工具在序列化和反序列化时，需要明确指定Class类型，因此此策略封装起来稍微复杂。【需要jackson-mapper-asl工具支持】
     *
     * 4）、OxmSerializer：提供了将javabean与xml之间的转换能力，目前可用的三方支持包括jaxb，apache-xmlbeans；redis存储的数据将是xml工具。不过使用此策略，编程将会有些难度，而且效率最低；不建议使用。【需要spring-oxm模块的支持】
     *
     * @param connectionFactory
     * @return
     */
    //创建redis操作模板对象
    //@Primary
    //@RefreshScope
    //@Bean("zdyJdkRedisTemplate")
    //@ConditionalOnBean(name = "connectionFactory")
    public RedisTemplate<String,Object> jdkRedisTemplate(@Qualifier("connectionFactory") RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        //默认的JDK序列化器
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer=new JdkSerializationRedisSerializer();

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om=new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        //定制序列化器，比如：可以采取AES对称加解密的方式来实现序列化和反序列化

        //redis 存入的key信息序列化,保证写入redis的key可读，不乱码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //redis 存入的value数据序列化，写入redis的value只要保证数据正确性和高效性即可，bytes<--->json<--->object 相互转换
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);


        //补充处理
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }


    //使用的是：connectionFactory
    //@RefreshScope
    //@Bean
    //@ConditionalOnBean(name = "connectionFactory")
    public CacheManager cacheManager(@Qualifier("connectionFactory") RedisConnectionFactory factory) {
        //SpringBoot新的改动导致
        RedisCacheManager redisCacheManager=RedisCacheManager.builder(factory).build();
        return redisCacheManager;
    }

}
