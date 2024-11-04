package com.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum BitlyPageEnableEnum {
    FALSE(0, "No"),
    TRUE(1, "Yes");
    @Getter
    private Integer code;
    @Getter
    private String desc;

    private static final Map<Integer, BitlyPageEnableEnum> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(BitlyPageEnableEnum::getCode, Function.identity()));

    public static BitlyPageEnableEnum getByCode(Integer code) {
        return MAP.get(code);
    }


}
