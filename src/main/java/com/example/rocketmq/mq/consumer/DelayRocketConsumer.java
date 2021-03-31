package com.example.rocketmq.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import com.example.rocketmq.service.OrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 功能描述 : 订单未支付检测
 *
 * @author Ziyear 2021-03-29 21:01
 */
@Slf4j
@Component
public class DelayRocketConsumer implements MessageListenerConcurrently {
    @Value("${rocket.demo.orderUnPayGroupName:orderUnPayGroupName}")
    private String orderUnPayGroupName;
    @Value("${rocketmq.namesrvAddr}")
    protected String namesrvAddr;
    @Value("${rocket.demo.orderUnPayTopic:orderUnPayTopic}")
    private String orderUnPayTopic;
    @Value("${rocket.demo.orderUnPayTag:orderUnPayTag}")
    private String orderUnPayTag;
    @Autowired
    private OrderService orderService;

    DefaultMQPushConsumer consumer;

    @PostConstruct
    public void init() {
        consumer = new DefaultMQPushConsumer(orderUnPayGroupName);
        // 同样也要设置NameServer地址
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe(orderUnPayTopic, orderUnPayTag);
            consumer.registerMessageListener(this);
            consumer.start();
            log.info("DelayRocketConsumer 启动成功 ！");

        } catch (Exception e) {
            log.error("DelayRocketConsumer 启动失败 ！", e);
        }
    }

    @PreDestroy
    public void destroy() {
        consumer.shutdown();
        log.info("DelayRocketConsumer 已注销 ！");

    }

    @SneakyThrows
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        log.info("DelayRocketConsumer 接收到消息了。。。。");
        for (MessageExt msg : msgs) {
            SendObjectDTO objectDTO = JSON.parseObject(msg.getBody(), SendObjectDTO.class);
            orderService.cancelOrder(objectDTO.getBizId());
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
