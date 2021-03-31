package com.example.rocketmq.model.response;

import lombok.Data;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-30 23:11
 */
@Data
public class ActionResult<T> {
    public static final String successCode = "000000";
    public static final String successMsg = "成功";

    public static final String errorCode = "999999";
    public static final String errorMsg = "失败";

    private String code;
    private String msg;
    private Boolean success;
    private T result;

    public ActionResult(String code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public ActionResult(String code, String msg, boolean success, T result) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.result = result;
    }

    public static <T> ActionResult<T> getSuccess() {
        return new ActionResult<>(successCode, successMsg, true);
    }

    public static <T> ActionResult<T> getError() {
        return new ActionResult<>(errorCode, errorMsg, false);
    }

    public static <T> ActionResult<T> getError(T result) {
        return new ActionResult<>(errorCode, errorMsg, false, result);
    }

    public static <T> ActionResult<T> getSuccess(T result) {
        return new ActionResult<>(successCode, successMsg, true, result);
    }
}
