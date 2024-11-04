package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum EventTypeEnum {
    LINK(0, "链接"),
    QR_CODE(1, "QR Code"),
    PAGE(2, "页面");

    private Integer code;

    private String desc;

    public static final Map<Integer, EventTypeEnum> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(EventTypeEnum::getCode, Function.identity()));

    public static Boolean checkTypeValid(Integer code) {
        return MAP.containsKey(code);
    }

    public static EventTypeEnum getByCode(Integer code) {
        return MAP.get(code);
    }

    public static String getDescByCode(Integer code) {
        return MAP.get(code).getDesc();
    }

}
