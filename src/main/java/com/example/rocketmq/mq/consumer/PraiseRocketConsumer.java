package com.example.rocketmq.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.example.rocketmq.model.dto.PraiseRecordDTO;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import com.example.rocketmq.service.PraiseService;
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
 * 功能描述 : 用户注册成功  发送优惠券到账户中
 *
 * @author Ziyear 2021-03-29 21:01
 */
@Slf4j
@Component
public class PraiseRocketConsumer implements MessageListenerConcurrently {
    @Value("${rocket.demo.praiseGroupName:praiseGroupName}")
    private String praiseGroupName;
    @Value("${rocketmq.namesrvAddr}")
    protected String namesrvAddr;
    @Value("${rocket.demo.praiseTopic:praiseTopic}")
    private String praiseTopic;
    @Value("${rocket.demo.praiseTag:praiseTag}")
    private String praiseTag;
    DefaultMQPushConsumer consumer;

    @Autowired
    private PraiseService praiseService;

    @PostConstruct
    public void init() {
        consumer = new DefaultMQPushConsumer(praiseGroupName);
        // 同样也要设置NameServer地址
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe(praiseTopic, praiseTag);
            consumer.registerMessageListener(this);
            // 每次拉取的间隔，单位为毫秒
            consumer.setPullInterval(2000);
            // 设置每个队列每次拉取的最大消息数为16
            consumer.setPullBatchSize(16);
            consumer.start();
            log.info("PraiseRocketConsumer 启动成功 ！");
        } catch (Exception e) {
            log.error("PraiseRocketConsumer 启动失败 ！", e);
        }
    }

    @PreDestroy
    public void destroy() {
        consumer.shutdown();
        log.info("PraiseRocketConsumer 已注销 ！");
    }

    @SneakyThrows
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        log.info("PraiseRocketConsumer 接收到消息了。。。。");
        for (MessageExt msg : msgs) {
            SendObjectDTO objectDTO = JSON.parseObject(msg.getBody(), SendObjectDTO.class);
            PraiseRecordDTO praiseRecordDTO = JSON.parseObject(objectDTO.getBody(), PraiseRecordDTO.class);

            praiseService.praise(praiseRecordDTO);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
