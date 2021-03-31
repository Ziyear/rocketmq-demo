package com.example.rocketmq.common;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 22:51
 */
public enum CouponStatusEnum implements Code {
    /**
     * 已使用
     */
    USED("used", "已使用"),
    /**
     * 未使用
     */
    UNUSED("unused", "未使用"),
    ;

    private String code;
    private String desc;

    CouponStatusEnum(String code, String desc) {
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