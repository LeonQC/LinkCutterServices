package com.llh.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "1rXiJl7VqDpW13Udsqs/KECgIftGzwk8srFhU3o50H8=";

    // 生成 JWT
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)  // 包含的附加信息
                .setSubject(username)  // 用户标识信息
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 令牌签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 用 HS256 算法签名
                .compact();  // 生成最终的令牌字符串
    }

    // 验证 JWT
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 提取用户名
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 提取所有信息
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    // 判断是否过期
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 提取过期时间
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}

