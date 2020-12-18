package com.zdy.mystarter.mq.rabbitmq.receiver;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 自产自销-1号消费者，靠监听器来处理
 * @author
 * @Description: 消息消费者2号
 * @date 2019/7/30 15:12
 */
@Component
public class SecondConsumer {

    /**
     * 这里监听了两个queues，只要其中一个有消息就会消费
     */
    @RabbitListener(queues = {"xfwl-queue-1", "xfwl-queue-2"},
                    containerFactory = "rabbitListenerContainerFactory"
    )
    public void handleMessage(String message) throws Exception {
        // 处理消息
        System.out.println("SecondConsumer {} handleMessage :" + message);
    }
}
