package com.example.rocketmq.service;

import com.example.rocketmq.common.BizTypeEnum;
import com.example.rocketmq.common.CouponStatusEnum;
import com.example.rocketmq.common.MQTypeEnum;
import com.example.rocketmq.common.PayStatusEnum;
import com.example.rocketmq.model.dto.CreateOrderDTO;
import com.example.rocketmq.model.entity.Commodity;
import com.example.rocketmq.model.entity.Coupon;
import com.example.rocketmq.model.entity.Order;
import com.example.rocketmq.model.entity.OrderItem;
import com.example.rocketmq.model.entity.User;
import com.example.rocketmq.mq.RocketProducerFactory;
import com.example.rocketmq.mq.dto.SendObjectDTO;
import com.example.rocketmq.repository.CommodityRepository;
import com.example.rocketmq.repository.CouponRepository;
import com.example.rocketmq.repository.OrderItemRepository;
import com.example.rocketmq.repository.OrderRepository;
import com.example.rocketmq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 21:38
 */
@Service
public class OrderService {
    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;

    /**
     * 创建订单
     *
     * @return
     */
    public void createOrder(CreateOrderDTO orderDTO) {
        Order order = new Order();
        Integer userId = orderDTO.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("用户不存在");
        }
        order.setUserId(userOptional.get().getId());
        List<CreateOrderDTO.OrderItemDTO> orderItems = orderDTO.getOrderItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CreateOrderDTO.OrderItemDTO orderItemDTO : orderItems) {
            Integer commodityId = orderItemDTO.getCommodityId();
            Integer quantity = orderItemDTO.getQuantity();
            Optional<Commodity> commodityOptional = commodityRepository.findById(commodityId);
            if (commodityOptional.isPresent()) {
                Commodity commodity = commodityOptional.get();
                BigDecimal multiply = commodity.getAmount().multiply(new BigDecimal(quantity));
                totalAmount = totalAmount.add(multiply);

                OrderItem orderItem = new OrderItem();
                orderItem.setCommodityId(commodity.getId());
                orderItem.setQuantity(quantity);
                orderItemList.add(orderItem);
            }
        }
        List<Coupon> coupons = couponRepository.findByUserId(userId);
        BigDecimal fullTotalAmount = BigDecimal.ZERO;
        BigDecimal fullUseAmount = BigDecimal.ZERO;

        List<Coupon> useCoupon = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (CouponStatusEnum.USED.equalsCode(coupon.getStatus())) {
                continue;
            }
            BigDecimal total = fullTotalAmount.add(coupon.getFullTotalAmount());
            if (total.compareTo(totalAmount) > 0) {
                continue;
            }
            fullTotalAmount = total;
            fullUseAmount = fullUseAmount.add(coupon.getFullUseAmount());
            useCoupon.add(coupon);
        }
        order.setTotalAmount(totalAmount);
        order.setPaymentAmount(totalAmount.subtract(fullUseAmount));
        order.setPayStatus(PayStatusEnum.PAY_INIT.getCode());
        orderRepository.save(order);
        orderItemList.forEach(orderItem -> {
            orderItem.setOrderId(order.getId());
        });
        orderItemRepository.saveAll(orderItemList);
        useCoupon.forEach(coupon -> {
            coupon.setOrderId(order.getId());
            coupon.setStatus(CouponStatusEnum.USED.getCode());
        });
        couponRepository.saveAll(useCoupon);
        // 发送订单取消定时消息
        // 测试阶段 使用演示级别 5， 1分钟
        SendObjectDTO sendData = new SendObjectDTO();
        sendData.setBizType(BizTypeEnum.ORDER_PAYMENT_TIMEOUT.getCode());
        sendData.setBizId(order.getId());
        sendData.setDelayLevel(5);
        RocketProducerFactory.getProduct(MQTypeEnum.DELAY).send(sendData);
    }


    public void cancelOrder(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (PayStatusEnum.PAY_INIT.equalsCode(order.getPayStatus())) {
                order.setPayStatus(PayStatusEnum.PAY_CANCEL.getCode());
                orderRepository.save(order);
                List<Coupon> coupons = couponRepository.findByOrderId(orderId);
                if (!CollectionUtils.isEmpty(coupons)) {
                    coupons.forEach(coupon -> {
                        coupon.setOrderId(null);
                        coupon.setStatus(CouponStatusEnum.UNUSED.getCode());
                    });
                    couponRepository.saveAll(coupons);
                }
            }
        }
    }
}
