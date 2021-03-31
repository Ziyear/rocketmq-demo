package com.example.rocketmq.mq.impl;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.common.MQTypeEnum;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 功能描述 : 用户注册成功  发送优惠券到账户中
 *
 * @author Ziyear 2021-03-29 21:01
 */
@Service
public class DefaultRocketProducer extends AbstractRocketProducer {
    @Value("${rocket.demo.registerTopic:registerTopic}")
    private String registerTopic;
    @Value("${rocket.demo.registerTag:registerTag}")
    private String registerTag;

    @Override
    public SendResult send(SendObjectDTO sendData) {
        SendResult send = null;
        try {
            Message msg = new Message(registerTopic, registerTag,
                    (JSON.toJSONString(sendData)).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            send = producer.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return send;
    }

    @Override
    public String getType() {
        return MQTypeEnum.DEFAULT.name();
    }
}
