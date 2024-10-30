package com.domain.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class AccountDetailDto implements Serializable {

    private String username;

    private String nickname;

    private String email;

    private String phoneNumber;

    private String createMonth;

    private String createDay;
}
