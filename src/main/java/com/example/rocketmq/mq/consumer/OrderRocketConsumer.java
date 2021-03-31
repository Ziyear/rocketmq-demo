package com.example.rocketmq.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import com.example.rocketmq.service.OrderOperateService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 功能描述 : 订单支付成功， 打印出库订单 仓库分拣出库 物流打包发货
 *
 * @author Ziyear 2021-03-29 21:01
 */
@Slf4j
@Component
public class OrderRocketConsumer extends AbstractRocketMqConsumer implements MessageListenerOrderly {
    @Value("${rocket.demo.orderPayedGroupName:orderPayedGroupName}")
    private String orderPayedGroupName;
    @Value("${rocketmq.namesrvAddr}")
    protected String namesrvAddr;
    @Value("${rocket.demo.orderPayedTopic:orderPayedTopic}")
    private String orderPayedTopic;
    @Value("${rocket.demo.orderPayedTag:orderPayedTag}")
    private String orderPayedTag;

    @Autowired
    private OrderOperateService orderOperateService;

    DefaultMQPushConsumer consumer;

    @PostConstruct
    public void init() {
        consumer = new DefaultMQPushConsumer(orderPayedGroupName);
        // 同样也要设置NameServer地址
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe(orderPayedTopic, orderPayedTag);
            consumer.registerMessageListener(this);
            consumer.start();
            log.info("OrderRocketConsumer 启动成功 ！");
        } catch (Exception e) {
            log.error("OrderRocketConsumer 启动失败 ！", e);
        }
    }

    @PreDestroy
    public void destroy() {
        consumer.shutdown();
        log.info("OrderRocketConsumer 已注销 ！");

    }

    @SneakyThrows
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        log.info("OrderRocketConsumer 接收到消息了。。。。");
        for (MessageExt msg : msgs) {
            SendObjectDTO objectDTO = JSON.parseObject(msg.getBody(), SendObjectDTO.class);
            Integer orderId = objectDTO.getBizId();
            String body = objectDTO.getBody();
            orderOperateService.operate(orderId, body);
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
