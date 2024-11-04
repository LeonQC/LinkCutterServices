package com.llh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum SceneEnum {
    MEMBER_LOGIN(0, "user-sms-login", "手机号登录"),
    MEMBER_UPDATE_MOBILE(1, "user-sms-update-phone", "修改手机号"),
    MEMBER_UPDATE_EMAIL(2, "user-sms-update-email", "修改邮箱"),
    MEMBER_FORGET_PASSWORD(3, "user-sms-reset-password", "重置密码"),
    MEMBER_REGISTER(4, "user-sms-register", "注册账号");

    private Integer scene;

    private String desc;

    private String message;

    private static final Map<Integer, SceneEnum> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(SceneEnum::getScene, Function.identity()));

    public static SceneEnum getByCode(Integer code) {
        return MAP.get(code);
    }

    public static String getDescByCode(Integer code) {
        return MAP.get(code).desc;
    }

    public static boolean checkTypeValid(Integer code) {
        return MAP.containsKey(code);
    }


}
