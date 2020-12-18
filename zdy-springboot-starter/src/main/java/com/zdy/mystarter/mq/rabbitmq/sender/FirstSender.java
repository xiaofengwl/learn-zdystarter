package com.zdy.mystarter.mq.rabbitmq.sender;

import com.zdy.mystarter.mq.rabbitmq.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author
 * @Description: 消息发送  生产者1
 * @date 2019/7/30 15:11
 */
@Slf4j
@Component
public class FirstSender {

    //mq模板工具bean
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param uuid
     * @param message 消息
     */
    public void send(String uuid, Object message) {
        CorrelationData correlationId = new CorrelationData(uuid);
        //配置交换机，队列key1，消息
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,
                                      RabbitMqConfig.ROUTINGKEY1,
                                      message,
                                      correlationId);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,
                RabbitMqConfig.ROUTINGKEY2,
                message,
                correlationId);
    }
}
