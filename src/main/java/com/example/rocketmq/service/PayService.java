package com.example.rocketmq.service;

import com.example.rocketmq.common.BizTypeEnum;
import com.example.rocketmq.common.MQTypeEnum;
import com.example.rocketmq.common.OrderOperateEnum;
import com.example.rocketmq.common.PayStatusEnum;
import com.example.rocketmq.model.entity.Order;
import com.example.rocketmq.mq.RocketProducerFactory;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import com.example.rocketmq.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 22:21
 */
@Service
public class PayService {

    @Autowired
    private OrderRepository orderRepository;

    public void pay(Integer userId, Integer orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId);
        if (order == null) {
            throw new RuntimeException("订单不存在！");
        }
        if (!PayStatusEnum.PAY_INIT.equalsCode(order.getPayStatus())) {
            throw new RuntimeException("订单支付状态错误，不能支付！");
        }

        order.setPayStatus(PayStatusEnum.PAY_SUCCESS.getCode());
        orderRepository.save(order);
        //发送消息 打印订单 仓库出库 物流发货
        sendOrderMsg(order.getId(), OrderOperateEnum.PRINT_ORDER.getCode());
        sendOrderMsg(order.getId(), OrderOperateEnum.WAREHOUSE_OUT.getCode());
        sendOrderMsg(order.getId(), OrderOperateEnum.LOGISTICS_DELIVERY.getCode());
    }

    private void sendOrderMsg(Integer id, String body) {
        SendObjectDTO sendData = new SendObjectDTO();
        sendData.setBody(body);
        sendData.setBizId(id);
        sendData.setId(id);
        sendData.setBizType(BizTypeEnum.ORDER_PAYMENT_SUCCESSFUL.getCode());
        RocketProducerFactory.getProduct(MQTypeEnum.ORDER).send(sendData);
    }
}
