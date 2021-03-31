package com.example.rocketmq.repository;

import com.example.rocketmq.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByIdAndUserId(Integer id, Integer userId);
}
