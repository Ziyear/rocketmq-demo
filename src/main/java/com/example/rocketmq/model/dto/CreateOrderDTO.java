package com.example.rocketmq.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 21:43
 */
@Data
public class CreateOrderDTO {
    private Integer userId;
    private List<OrderItemDTO> orderItems;
    @Data
    public static class OrderItemDTO {
        private Integer commodityId;
        private Integer quantity;
    }
}


