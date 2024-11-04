package com.service;

import com.domain.dto.AuthSmsDto;
import com.domain.request.SmsCodeRequest;
import com.llh.utils.Result;
import org.springframework.stereotype.Service;

@Service
public interface SmsService {
    public Result<Boolean> verifySmsCode(AuthSmsDto authSmsDto);

    public Result<Boolean> sendSmsCode(SmsCodeRequest smsCodeRequest);
}

