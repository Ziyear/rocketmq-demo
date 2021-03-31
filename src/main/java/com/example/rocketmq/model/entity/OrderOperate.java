package com.example.rocketmq.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 功能描述 : 订单操作表
 *
 * @author Ziyear 2021-03-30 22:29
 */
@Entity
@Table(name = "t_order_operate")
@Data
public class OrderOperate {
    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作信息
     */
    private String operate;
}
