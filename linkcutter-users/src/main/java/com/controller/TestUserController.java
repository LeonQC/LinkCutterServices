package com.controller;

import com.domain.request.UserInfoRequest;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping(path = "/user")
public class TestUserController {

    // 创建 Logger 实例
    private static final Logger logger = LoggerFactory.getLogger(TestUserController.class);

    @GetMapping("/test")
    public String test() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64EncodedKey = Encoders.BASE64.encode(key.getEncoded());

        // 输出日志，记录密钥信息
        logger.info("Base64 Encoded Key: " + base64EncodedKey);

        return "user test";
    }

    @PostMapping("/test1")
    public String test1(@RequestParam("name") String name) {
        return name;
    }

    @PostMapping("/test2")
    public String userInfoSetupController(@RequestBody UserInfoRequest userInfoRequest) {
        logger.info("Received UserInfoRequest: {}", userInfoRequest.getUsername());
        return userInfoRequest.getUsername();
    }

}