package com.example.rocketmq.mq;

import com.example.rocketmq.common.MQTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产者工厂
 */
@Component
public class RocketProducerFactory implements ApplicationContextAware {

    private static Map<String, RocketProducer> producerMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RocketProducer> map = applicationContext.getBeansOfType(RocketProducer.class);
        producerMap = new HashMap<>();
        map.forEach((key, value) -> producerMap.put(value.getType(), value));
    }

    public static RocketProducer getProduct(MQTypeEnum mqTypeEnum) {
        return producerMap.get(mqTypeEnum.name());
    }
}