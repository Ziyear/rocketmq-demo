package com.example.rocketmq.mq.dto;

import lombok.Data;

/**
 * 功能描述 : 消息发送dto
 *
 * @author Ziyear 2021-03-29 21:44
 */
@Data
public class SendObjectDTO {
    /**
     * 顺序消息id标记
     */
    private Integer id;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务id
     */
    private Integer bizId;

    /**
     * 消息体
     */
    private String body;

    /**
     * 延时级别
     * 默认的配置是messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h，
     */
    private Integer delayLevel;
}
