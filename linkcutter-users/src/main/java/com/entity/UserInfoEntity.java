package com.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_table")
@Data
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createTime;

    private LocalDateTime lastLogin;

    private LocalDateTime lastOperateTime;

    private String ip;

    @Column(nullable = false)
    private short priority = 0;

    private String nickname;

    @Column(unique = true)
    private String phoneNumber;
}
