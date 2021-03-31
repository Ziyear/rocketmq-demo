package com.example.rocketmq.controller;

import com.example.rocketmq.model.dto.CreateOrderDTO;
import com.example.rocketmq.model.request.CreateOrderRequest;
import com.example.rocketmq.model.request.PayRequest;
import com.example.rocketmq.model.request.RegisterRequest;
import com.example.rocketmq.model.response.ActionResult;
import com.example.rocketmq.service.OrderService;
import com.example.rocketmq.service.PayService;
import com.example.rocketmq.service.PraiseService;
import com.example.rocketmq.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 23:08
 */
@RestController
@RequestMapping("/api/user")
public class DemoController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;
    @Autowired
    private PraiseService praiseService;

    @GetMapping("/praise")
    public ActionResult<Void> praise(@RequestParam("userId") Integer userId, @RequestParam("liveName") String liveName) {
        praiseService.push(userId, liveName);
        return ActionResult.getSuccess();
    }

    @PostMapping("/register")
    public ActionResult<Void> register(RegisterRequest request) {
        registerService.register(request.getUsername(), request.getPassword());
        return ActionResult.getSuccess();
    }

    @PostMapping("/createOrder")
    public ActionResult<Void> createOrder(CreateOrderRequest request) {
        CreateOrderDTO orderDTO = new CreateOrderDTO();
        orderDTO.setUserId(request.getUserId());
        List<CreateOrderRequest.CommodityInfo> commodityInfos = request.getCommodityInfos();

        List<CreateOrderDTO.OrderItemDTO> orderItems = new ArrayList<>();
        for (CreateOrderRequest.CommodityInfo commodityInfo : commodityInfos) {
            CreateOrderDTO.OrderItemDTO orderItemDTO = new CreateOrderDTO.OrderItemDTO();
            orderItemDTO.setCommodityId(commodityInfo.getCommodityId());
            orderItemDTO.setQuantity(commodityInfo.getQuantity());
            orderItems.add(orderItemDTO);
        }
        orderDTO.setOrderItems(orderItems);
        orderService.createOrder(orderDTO);
        return ActionResult.getSuccess();
    }

    @PostMapping("/pay")
    public ActionResult<Void> pay(PayRequest request) {
        payService.pay(request.getUserId(), request.getOrderId());
        return ActionResult.getSuccess();
    }
}
