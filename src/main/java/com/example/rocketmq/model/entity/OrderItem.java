package com.example.rocketmq.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-29 22:35
 */
@Entity
@Table(name = "t_order_item")
@Data
public class OrderItem {
    @Id
    @GeneratedValue()
    private Integer id;

    private Integer orderId;

    private Integer commodityId;

    private Integer quantity;
}
