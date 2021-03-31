package com.example.rocketmq.model.request;

import lombok.Data;

@Data
public class PayRequest {
    private Integer userId;
    private Integer orderId;
}
