package com.example.rocketmq.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 功能描述 :满减总金额
 *
 * @author Ziyear 2021-03-29 22:25
 */
@Entity
@Table(name = "t_couponr")
@Data
public class Coupon {
    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 用户id
     */
    private String status;

    /**
     * 使用金额
     */
    private BigDecimal fullUseAmount;

    /**
     * 需满足的金额
     */
    private BigDecimal fullTotalAmount;
}
