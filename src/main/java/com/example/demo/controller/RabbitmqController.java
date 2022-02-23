package com.example.demo.controller;

import com.example.demo.service.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitmqController {
    @Autowired
    private RabbitmqService rabbitmqService;

    @RequestMapping("/sendWork")
    public Object sendWork() {
        rabbitmqService.sendWork();
        return "发送成功...";
    }

    @RequestMapping("/consumeWork")
    public Object consumeWork() {
        rabbitmqService.sendWork();
        return "发送成功...";
    }
}
