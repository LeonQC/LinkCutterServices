package com.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name = "url_table")
@Data
public class UrlTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 对应数据库的主键

    @Column(name = "short_code", length = 500)  // 设置长度为 500
    private String shortCode;  // 短链接

    @Column(name = "original_url", nullable = false, length = 1000)  // 设置长度为 500
    private String originalUrl;  // 原始URL

    @Column(name = "create_time", nullable = true)
    private LocalDateTime createTime;  // 创建时间

    @Column(name = "user_id", nullable = true)
    private Long userId;  // 用户ID

    @Column(name = "username", nullable = true, length = 255)  // 可以自定义长度
    private String username;  // 用户名

    @Column(name = "title", nullable = true, length = 255)  // 可以自定义长度
    private String title;  // 链接的标题

    @Column(name = "qr_code_id", nullable = true, length = 500)  // 设置长度为 500
    private String qrCodeId;  // 二维码的ID

    @Column(name = "back_half_code", nullable = true, length = 500)  // 设置长度为 500
    private String backHalfCode;

    private Integer deletedFlag = 0;

    private Integer eventType;

    private LocalDateTime expireTime;

    @Column(name = "shortened_url")
    private String shortenedUrl;
}