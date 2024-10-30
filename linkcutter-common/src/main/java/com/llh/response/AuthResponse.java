package com.llh.response;

import lombok.AllArgsConstructor;

public class AuthResponse {
    private String token;

    // 构造方法
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter 方法
    public String getToken() {
        return token;
    }

    // Setter 方法（如果需要，可以省略）
    public void setToken(String token) {
        this.token = token;
    }
}