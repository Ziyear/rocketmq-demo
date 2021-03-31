package com.example.rocketmq.model.request;

import lombok.Data;

import java.util.List;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 23:10
 */
@Data
public class CreateOrderRequest {
    private Integer userId;

    private List<CommodityInfo> commodityInfos;

    @Data
    public static class CommodityInfo {
        private Integer commodityId;
        private Integer quantity;
    }
}
