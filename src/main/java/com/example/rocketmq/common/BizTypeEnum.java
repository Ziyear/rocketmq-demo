package com.example.rocketmq.common;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 21:30
 */
public enum BizTypeEnum implements Code {
    /**
     * 新人优惠券
     */
    COUPONS_FOR_NEWCOMERS("COUPONS_FOR_NEWCOMERS", "新人优惠券"),
    /**
     * 订单付款超时
     */
    ORDER_PAYMENT_TIMEOUT("ORDER_PAYMENT_TIMEOUT", "订单付款超时"),
    /**
     * 订单支付成功
     */
    ORDER_PAYMENT_SUCCESSFUL("ORDER_PAYMENT_SUCCESSFUL", "订单支付成功"),
    ;

    private String code;
    private String desc;

    BizTypeEnum(String code, String desc) {
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
