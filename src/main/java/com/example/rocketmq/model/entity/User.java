package com.example.rocketmq.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能描述 : 用户
 *
 * @author Ziyear 2021-03-29 22:12
 */
@Entity
@Table(name = "t_user")
@Data
public class User {

    @Id
    @GeneratedValue()
    private Integer id;

    private String name;

    private String password;
}
