package com.llh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum PhoneCode {
    CHINA(0, "+86", 11),
    NORTH_AMERICA(1, "+1", 10),
    UK(2, "+44", 10),
    INDIA(3, "+91", 10),
    AUSTRALIA(4, "+61", 9),
    GERMANY(5, "+49", 11),
    FRANCE(6, "+33", 9),
    JAPAN(7, "+81", 10),
    SOUTH_KOREA(8, "+82", 10);

    private final int code;
    private final String start;
    private final int numberLength;

    // 根据前缀查找国家
    public static PhoneCode getByPrefix(String prefix) {
        for (PhoneCode phoneCode : values()) {
            if (phoneCode.getStart().equals(prefix)) {
                return phoneCode;
            }
        }
        return null;
    }

    private static final Map<String, PhoneCode> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(PhoneCode::getStart, Function.identity()));


    public static boolean checkTypeValid(String start) {
        return MAP.containsKey(start);
    }

    public static int getNumberLengthByPrefix(String prefix) {
        PhoneCode phoneCode = MAP.get(prefix);
        return phoneCode == null ? 0 : phoneCode.getNumberLength();
    }

    public static String getPrefixByPhoneCode(PhoneCode code) {
        for (PhoneCode phoneCode : PhoneCode.values()) {
            if (phoneCode.equals(code)) {
                return phoneCode.getStart();
            }
        }
        return null;
    }

}