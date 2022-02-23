package com.example.demo.service;

import com.example.demo.user.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqMapper {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendWork() {
        User u1= new User();
        u1.setId(1);
        u1.setName("aa");
        u1.setPassword("123");
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("exchange_fanout", "", u1);
        }
    }

    /*@RabbitListener(queues = "queue_work")
    public void consumeWork(String msg) {
        System.out.println("消费者接收到：" + msg);
    }*/
}
