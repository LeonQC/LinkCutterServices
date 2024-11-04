package com.controller;

import com.domain.dto.AuthSmsDto;
import com.domain.request.SmsCodeRequest;
import com.llh.utils.Result;
import com.llh.utils.ResultUtil;
import com.service.SmsService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/sendCode")
    public Result<Boolean> sendSmsCode(@RequestBody SmsCodeRequest smsCodeRequest) {
        return smsService.sendSmsCode(smsCodeRequest);
    }

    @GetMapping("/verifyCode")
    public Result<Boolean> verifyCode(@RequestBody AuthSmsDto authSmsDto) {
        return smsService.verifySmsCode(authSmsDto);
    }
}
