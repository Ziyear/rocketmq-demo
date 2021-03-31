package com.example.rocketmq.common;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 22:13
 */
public enum PayStatusEnum implements Code {
    /**
     * 初始状态
     */
    PAY_INIT("PAY_INIT", "初始状态"),
    /**
     * 支付成功
     */
    PAY_SUCCESS("PAY_SUCCESS", "支付成功"),

    /**
     * 支付取消
     */
    PAY_CANCEL("PAY_CANCEL", "已取消"),
    ;

    private String code;
    private String desc;

    PayStatusEnum(String code, String desc) {
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
