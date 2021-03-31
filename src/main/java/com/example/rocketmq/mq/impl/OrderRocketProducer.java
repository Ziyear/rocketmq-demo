package com.example.rocketmq.mq.impl;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.common.MQTypeEnum;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述 : 订单支付成功， 打印出库订单 仓库分拣出库 物流打包发货
 *
 * @author Ziyear 2021-03-29 21:01
 */
@Service
public class OrderRocketProducer extends AbstractRocketProducer {

    @Value("${rocket.demo.orderPayedTopic:orderPayedTopic}")
    private String orderPayedTopic;
    @Value("${rocket.demo.orderPayedTag:orderPayedTag}")
    private String orderPayedTag;

    @Override
    public SendResult send(SendObjectDTO sendData) {
        SendResult sendResult = null;
        try {
            Message msg = new Message(orderPayedTopic, orderPayedTag, (JSON.toJSONString(sendData)).getBytes());
            sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, sendData.getId());
        } catch (MQClientException | InterruptedException | MQBrokerException | RemotingException e) {
            e.printStackTrace();
        }
        return sendResult;
    }

    @Override
    public String getType() {
        return MQTypeEnum.ORDER.name();
    }
}
