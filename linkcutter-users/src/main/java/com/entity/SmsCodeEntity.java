package com.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sms_code")
public class SmsCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 手机号码前缀（国家代码）
    @Column(name = "phone_header", nullable = false)
    private String phoneHeader;

    // 手机号码
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    // 手机号码长度
    @Column(name = "phone_number_length", nullable = false)
    private Integer phoneNumberLength;

    // 短信验证码
    @Column(name = "sms_code", nullable = false)
    private String smsCode;

    // 验证码的生成时间
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    // 验证码过期时间
    @Column(name = "expire_time", nullable = false)
    private LocalDateTime expireTime;

    // 是否已经使用过
    @Column(name = "used", nullable = false)
    private Boolean used = false;

    @Column(name = "scene", nullable = false)
    private Integer scene;
}