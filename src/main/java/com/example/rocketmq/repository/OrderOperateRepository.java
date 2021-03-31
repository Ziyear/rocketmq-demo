package com.example.rocketmq.repository;

import com.example.rocketmq.model.entity.OrderOperate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOperateRepository extends JpaRepository<OrderOperate, Integer> {
}
