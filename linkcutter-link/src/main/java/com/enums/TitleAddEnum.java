package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum TitleAddEnum {
    FALSE(0, "No"),
    TRUE(1, "Yes");
    @Getter
    private Integer code;
    @Getter
    private String desc;

    private static final Map<Integer, TitleAddEnum> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(TitleAddEnum::getCode, Function.identity()));

    public static TitleAddEnum getEnum(Integer code) {
        return MAP.get(code);
    }
}
