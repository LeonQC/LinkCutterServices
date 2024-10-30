package com.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto implements Serializable {

    private Integer id;

    private String username;

    private String email;

    private LocalDateTime createTime;

    private LocalDateTime lastLogin;

    private LocalDateTime lastOperateTime;

    private String ip;

    private short priority;

    private String nickname;

    private String phoneNumber;
}
