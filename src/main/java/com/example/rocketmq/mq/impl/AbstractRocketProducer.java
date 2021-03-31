package com.example.rocketmq.mq.impl;

import com.example.rocketmq.mq.RocketProducer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;

public abstract class AbstractRocketProducer implements RocketProducer {
    @Autowired
    protected DefaultMQProducer producer;

    @PreDestroy
    public void destroy() {
        producer.shutdown();
    }
}
