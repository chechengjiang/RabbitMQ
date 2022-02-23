package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RabbitmqConfig {
    // 配置一个工作模型队列
    @Bean
    public Queue queueWork1() {
        return new Queue("queue_work");
    }

    @Bean
    public Queue queueFanout1() {
        return new Queue("queue_fanout1");
    }
    @Bean
    public Queue queueFanout2() {
        return new Queue("queue_fanout2");
    }
    @Bean
    public Queue queueFanout3() {
        return new Queue("queue_fanout3",false);
    }
    @Bean
    public Queue queueFanout4() {
        return new Queue("queue_fanout4",false,false,true);
    }
    // 准备一个交换机
    @Bean
    public FanoutExchange exchangeFanout() {
        return new FanoutExchange("exchange_fanout");
    }
    // 将交换机和队列进行绑定
    @Bean
    public Binding bindingExchange1() {
        return BindingBuilder.bind(queueFanout1()).to(exchangeFanout());
    }
    @Bean
    public Binding bindingExchange2() {
        return BindingBuilder.bind(queueFanout2()).to(exchangeFanout());
    }

    @Bean
    public Binding bindingExchange3() {
        return BindingBuilder.bind(queueFanout3()).to(exchangeFanout());
    }

    @Bean
    public Binding bindingExchange4() {
        return BindingBuilder.bind(queueFanout4()).to(exchangeFanout());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // 如果发送到交换器都没有成功（比如说删除了交换器），ack 返回值为 false
            // 如果发送到交换器成功，但是没有匹配的队列（比如说取消了绑定），ack 返回值为还是 true （这是一个坑，需要注意）
            if (ack) {
                String messageId = correlationData.getId();
                System.out.println("confirm:"+messageId);
            }
        });
        // 设置 ReturnCallback 回调   yml需要配置 publisher-returns: true
        // 如果发送到交换器成功，但是没有匹配的队列，就会触发这个回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText,
                                          exchange, routingKey) -> {
            String messageId = message.getMessageProperties().getMessageId();
            System.out.println("return:"+messageId);
        });
        return rabbitTemplate;
    }

}