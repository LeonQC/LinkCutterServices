package com.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum Priority {
    NORMAL(0, "普通用户"),
    UPGRADED(1, "VIP会员");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    public static final Map<Integer, Priority> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(Priority::getCode, Function.identity()));

    public static Priority getByCode(Integer code) {
        return MAP.get(code);
    }
}
