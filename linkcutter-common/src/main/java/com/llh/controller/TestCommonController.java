package com.llh.controller;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequestMapping(path = "/common")
public class TestCommonController {
    private static final Logger logger = LoggerFactory.getLogger(TestCommonController.class);
    @GetMapping("/test")
    public String test(){
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64EncodedKey = Encoders.BASE64.encode(key.getEncoded());

        // 输出日志，记录密钥信息
        logger.info("Base64 Encoded Key: " + base64EncodedKey);
        return "common test";
    }

}
