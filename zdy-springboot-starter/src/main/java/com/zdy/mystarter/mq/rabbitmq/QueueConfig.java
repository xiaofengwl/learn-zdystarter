package com.zdy.mystarter.mq.rabbitmq;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @Description: 队列配置
 * @date 2019/7/30 15:09
 */
@Configuration
public class QueueConfig {

    /**
     * TODO 第一个队列
     * @return
     */
    @Bean
    public Queue firstQueue() {
        /**
         * todo 参数说明：
         *   1、第一个参数： 队列名称
         *   2、第二个参数： durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
         *   3、第三个参数： exclusive  表示该消息队列是否只在当前connection生效,默认是false
         *   4、第四个参数： auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         */
        return new Queue("xfwl-queue-1",
                         true,
                         false,
                         false);
    }

    /**
     * TODO 第二个队列（设置了消息有效期）
     * @return
     */
    @Bean
    public Queue secondQueue() {
        //设置多属性
        Map<String, Object> argss = new HashMap<String , Object>();
        argss.put("x-message-ttl" , 30*1000);//设置队列里消息的ttl的时间30s

        /**
         * todo 参数说明：
         *   1、第一个参数： 队列名称
         *   2、第二个参数： durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
         *   3、第三个参数： exclusive  表示该消息队列是否只在当前connection生效,默认是false
         *   4、第四个参数： auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         *   5、第五个参数： 一个map对象，用于多属性设置
         */
        return new Queue("xfwl-queue-2",
                         true,
                         false,
                         false,
                          argss);
    }
}
