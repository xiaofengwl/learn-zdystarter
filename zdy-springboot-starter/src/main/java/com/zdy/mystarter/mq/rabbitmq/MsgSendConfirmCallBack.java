package com.zdy.mystarter.mq.rabbitmq;


import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 消息发送后确认回调
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    /**
     * 消息被消费后的监听回调
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("MsgSendConfirmCallBack  , 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息消费成功");
        } else {
            System.out.println("消息消费失败:" + cause + "\n重新发送");
        }
    }
}
