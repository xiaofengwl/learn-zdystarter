package com.zdy.mystarter.mq.rabbitmq.controller;

import com.zdy.mystarter.mq.rabbitmq.sender.FirstSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author
 * @Description: 测试控制器
 * @date 2019/7/30 15:13
 */
@RestController
@RequestMapping("/rabbitmq")
public class SendController {

    @Autowired
    private FirstSender firstSender;

    /**
     * 自产自销
     * @param message
     * @return
     */
    @RequestMapping("/1")
    public String send(@RequestParam("message") String message) {
        String uuid = UUID.randomUUID().toString();
        firstSender.send(uuid, message);
        return uuid;
    }
}