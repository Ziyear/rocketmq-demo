package com.example.rocketmq.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * 功能描述 :满减总金额
 *
 * @author Ziyear 2021-03-29 22:25
 */
@Entity
@Table(name = "t_order")
@Data
public class Order {
    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实际支付金额
     */
    private BigDecimal paymentAmount;

    /**
     * 支付状态
     */
    private String payStatus;
}
