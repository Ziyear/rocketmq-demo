package com.example.rocketmq.common;

import java.util.Objects;


public interface Code {

    String getCode();

    default boolean equalsCode(String code) {
        return Objects.equals(code, getCode());
    }
}
