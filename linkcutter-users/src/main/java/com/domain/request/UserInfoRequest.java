package com.domain.request;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Data
public class UserInfoRequest implements Serializable {

    private String username;

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

}
