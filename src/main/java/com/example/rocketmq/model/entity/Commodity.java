package com.example.rocketmq.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 功能描述 :满减总金额
 *
 * @author Ziyear 2021-03-29 22:25
 */
@Entity
@Table(name = "t_commodity")
@Data
public class Commodity {
    @Id
    @GeneratedValue()
    private Integer id;

    private String name;

    private BigDecimal amount;

    private String desc;
}
