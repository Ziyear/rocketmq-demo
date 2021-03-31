package com.example.rocketmq.mq;

import com.example.rocketmq.mq.dto.SendObjectDTO;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * 功能描述 : 匹配
 *
 * @author Ziyear 2021-03-29 21:01
 */
public interface RocketProducer {

    SendResult send(SendObjectDTO sendData);

    String getType();
}
