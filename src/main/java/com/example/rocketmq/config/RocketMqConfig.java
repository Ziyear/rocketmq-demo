package com.example.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-25 21:40
 */
@Slf4j
@Configuration
public class RocketMqConfig {
    @Value("${rocketmq.groupName}")
    private String groupName;
    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Bean
    public DefaultMQProducer defaultMqProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            log.info("producer启动了。。。");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }
}