package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqServiceImpl implements RabbitmqService {
    @Autowired
    private RabbitmqMapper rabbitmqMapper;

    @Override
    public void sendWork() {
        rabbitmqMapper.sendWork();
    }

    @Override
    public void consumeWork() {

    }
}
