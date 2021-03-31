package com.example.rocketmq.common;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 23:33
 */
public enum OrderOperateEnum implements Code {
    /**
     * 打印订单
     */
    PRINT_ORDER("PRINT_ORDER", "打印订单"),
    /**
     * 仓库出库
     */
    WAREHOUSE_OUT("WAREHOUSE_OUT", "仓库出库"),
    /**
     * 物流发货
     */
    LOGISTICS_DELIVERY("LOGISTICS_DELIVERY", "物流发货"),
    ;

    private String code;
    private String desc;

    OrderOperateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
