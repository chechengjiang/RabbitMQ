package com.example.demo.service;

import com.example.demo.user.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;

@Component
public class PublishReceiveListener {

    @RabbitListener(queues = "queue_fanout4")
    public void receiveMsg1(User msg,Channel channel) {
        System.out.println("队列1接收到消息：" + msg);
    }

    @RabbitListener(queues = "queue_fanout4")
    public void receiveMsg2(User msg) {
        System.out.println("队列2接收到消息：" + msg);
    }
}
