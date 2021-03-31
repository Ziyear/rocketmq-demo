package com.example.rocketmq.mq.impl;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.common.MQTypeEnum;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 功能描述 : 订单未支付检测
 *
 * @author Ziyear 2021-03-29 21:01
 */
@Service
public class DelayRocketProducer extends AbstractRocketProducer {
    @Value("${rocket.demo.orderUnPayTopic:orderUnPayTopic}")
    private String orderUnPayTopic;
    @Value("${rocket.demo.orderUnPayTag:orderUnPayTag}")
    private String orderUnPayTag;

    @Override
    public SendResult send(SendObjectDTO sendData) {
        SendResult sendResult = null;
        try {
            Message msg = new Message(orderUnPayTopic, orderUnPayTag, (JSON.toJSONString(sendData)).getBytes());
            // 默认的配置是messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h，
            msg.setDelayTimeLevel(sendData.getDelayLevel());
            sendResult = producer.send(msg);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }
        return sendResult;
    }

    @Override
    public String getType() {
        return MQTypeEnum.DELAY.name();
    }
}
