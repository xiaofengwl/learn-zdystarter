package com.zdy.mystarter.mq.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value="classpath:/conf/rabbitmq.properties",ignoreResourceNotFound=false)
@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.addresses}")
    private String hosts;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    /**
     * todo 消息交换机的名字
     */
    public static final String EXCHANGE = "xfwl_Exchange";
    /**
     * todo 队列key1
     */
    public static final String ROUTINGKEY1 = "xfwl_queue_key1";
    /**
     * todo 队列key2
     */
    public static final String ROUTINGKEY2 = "xfwl_queue_key2";

    //注入
    @Autowired
    private QueueConfig queueConfig;
    @Autowired
    private ExchangeConfig exchangeConfig;

    /**
     * todo 连接工厂,使用默认的
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    @Primary
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setAddresses(hosts);
        cachingConnectionFactory.setUsername(userName);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        //设置连接工厂缓存模式：
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        //缓存连接数
        cachingConnectionFactory.setConnectionCacheSize(10);
        //设置连接限制
        cachingConnectionFactory.setConnectionLimit(20);
        return cachingConnectionFactory;
    }

    /**
     * todo 创建第一个Bingdings
     * todo 将消息队列1和交换机进行绑定，并指定key
     */
    @Bean
    public Binding binding_one() {
        return BindingBuilder.bind(queueConfig.firstQueue())        //绑定队列1
                             .to(exchangeConfig.directExchange())   //绑定DirectExchange交换器
                             .with(RabbitMqConfig.ROUTINGKEY1);     //设置binding-key1
    }

    /**
     * todo 创建第二个Bingdings
     * todo 将消息队列2和交换机进行绑定，并指定key
     */
    @Bean
    public Binding binding_two() {
        return BindingBuilder.bind(queueConfig.secondQueue())       //绑定队列2
                             .to(exchangeConfig.directExchange())   //绑定DirectExchange交换器
                             .with(RabbitMqConfig.ROUTINGKEY2);     //设置binding-key2
    }

    /**
     * todo 接口，消费端负责与RabbitMQ服务器保持连接并将Message传递给实际的@RabbitListener/@RabbitHandler处理
     * todo queue listener  观察 消费监听模式
     * 当有消息到达时会通知监听在对应的队列上的监听对象，然后通知消费者消费
     * 你会发现，生产者和消费者是脱耦的，
     * 消费者只要监听队列即可。
     * 生产者只需要发送消息。
     * 其他的关联配置，靠开发者的配置类来定制化装配完成。
     *
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer_one() {
        //1、监听容器
        SimpleMessageListenerContainer simpleMessageListenerContainer =
                                       new SimpleMessageListenerContainer(connectionFactory);
        //2、配置属性
        simpleMessageListenerContainer.addQueues(queueConfig.firstQueue(),
                                                 queueConfig.secondQueue());       //加入监听队列:队列1
        simpleMessageListenerContainer.setExposeListenerChannel(true);             //设置与消费者连接channel=true
        simpleMessageListenerContainer.setMaxConcurrentConsumers(5);               //设置当前最大消费者数量5
        simpleMessageListenerContainer.setConcurrentConsumers(3);                  //设置当前消费者数量1
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认

        return simpleMessageListenerContainer;
    }

    /**
     * todo 定义rabbit template用于数据的接收和发送
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        //1、根据连接factory，创建操作模板对象
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        /**
         * todo 说明1
         * 若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        //2、为操作模板对象绑定发送确认回调处理机制
        template.setConfirmCallback(msgSendConfirmCallBack());
        //template.setReturnCallback(msgSendReturnCallback());

        /**
         * todo 说明2
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        //  template.setMandatory(true);
        return template;
    }

    /**
     * todo 消息确认机制
     * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
     * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)
     * 在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
     *
     * @return
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack() {
        return new MsgSendConfirmCallBack();
    }

}
