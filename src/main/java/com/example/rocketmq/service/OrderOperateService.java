package com.example.rocketmq.service;

import com.example.rocketmq.model.entity.Order;
import com.example.rocketmq.model.entity.OrderOperate;
import com.example.rocketmq.repository.OrderOperateRepository;
import com.example.rocketmq.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-31 21:09
 */
@Service
public class OrderOperateService {
    @Autowired
    private OrderOperateRepository orderOperateRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void operate(Integer orderId, String operate) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            OrderOperate orderOperate = new OrderOperate();
            orderOperate.setOrderId(orderId);
            orderOperate.setOperate(operate);
            orderOperate.setOperateTime(new Date());
            orderOperateRepository.save(orderOperate);
        }
    }
}
