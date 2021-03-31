package com.example.rocketmq.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 功能描述 : 点赞记录表
 *
 * @author Ziyear 2021-03-31 21:24
 */
@Entity
@Table(name = "t_praise_record")
@Data
public class PraiseRecord {

    @Id
    @GeneratedValue()
    private Integer id;
    private Integer userId;
    private String liveName;
    private Date createTime;
}
