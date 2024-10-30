package com.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.domain.dto.AuthSmsDto;
import com.domain.request.SmsCodeRequest;
import com.entity.SmsCodeEntity;
import com.llh.enums.PhoneCode;
import com.llh.enums.ResultCode;
import com.llh.enums.SceneEnum;
import com.llh.exception.BusinessException;
import com.llh.utils.Result;
import com.llh.utils.ResultUtil;
import com.llh.utils.StringUtil;
import com.properties.SmsCodeProperties;
import com.repository.SmsCodeRepository;
import com.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsCodeRepository smsCodeRepository;

    @Autowired
    private SmsCodeProperties smsCodeProperties;

    public Result<Boolean> sendSmsCode(SmsCodeRequest smsCodeRequest) {
        // 校验手机号格式
        String phoneNumber = smsCodeRequest.getPhoneNumber();
        if (!StringUtil.isValidPhoneNumber(phoneNumber, smsCodeRequest.getStartNumber())) {
            return ResultUtil.wrapFailure(ResultCode.PHONE_NUMBER_ERROR, false);
        }

        // 检查发送频率
        if (!canSendSms(phoneNumber)) {
            return ResultUtil.wrapFailure(ResultCode.HIGH_SMS_REQUEST_RATE_ERROR, false);
        }

        // 生成验证码
        try {
            String smsCode = generateRandomCode(smsCodeProperties.getBeginCode(), smsCodeProperties.getEndCode());
            Integer scene = smsCodeRequest.getScene();
            String startNumber = smsCodeRequest.getStartNumber();
            if (!SceneEnum.checkTypeValid(scene) || !PhoneCode.checkTypeValid(startNumber)) {
                return ResultUtil.wrapFailure(ResultCode.PHONE_NUMBER_ERROR, false);
            }

            saveSmsCode(phoneNumber, smsCode, scene, startNumber);

            boolean res = sendToSmsGateway(phoneNumber, smsCode, SceneEnum.getDescByCode(scene));
            return res ? ResultUtil.wrapSuccess(res, "发送成功！")
                    : ResultUtil.wrapFailure(ResultCode.PHONE_NUMBER_ERROR, false);
        }
        catch (BusinessException e) {
            throw new BusinessException(ResultCode.NETWORK_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // 生成随机验证码
    private String generateRandomCode(int beginCode, int endCode) {
        Random random = new Random();
        return String.valueOf(random.nextInt(endCode - beginCode) + beginCode);
    }

    // 验证发送频率
    private boolean canSendSms(String phoneNumber) {
        SmsCodeEntity lastCode = smsCodeRepository.findFirstByPhoneNumberOrderByCreatedTimeDesc(phoneNumber);
        if (lastCode == null) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        return lastCode.getCreatedTime().plus(smsCodeProperties.getSendFrequency()).isBefore(now);
    }

    // 保存验证码到数据库
    private void saveSmsCode(String phoneNumber, String smsCode, Integer scene, String startNumber) {
        SmsCodeEntity smsCodeEntity = new SmsCodeEntity();
        smsCodeEntity.setPhoneHeader(startNumber);
        smsCodeEntity.setPhoneNumber(phoneNumber);
        smsCodeEntity.setPhoneNumberLength(phoneNumber.length());
        smsCodeEntity.setSmsCode(smsCode);
        smsCodeEntity.setCreatedTime(LocalDateTime.now());
        smsCodeEntity.setExpireTime(LocalDateTime.now().plus(smsCodeProperties.getExpireTime()));
        smsCodeEntity.setUsed(false);
        smsCodeEntity.setScene(scene);
        smsCodeRepository.save(smsCodeEntity);
    }

    // 创建阿里云客户端
    public static com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考。
        // 建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html。
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId("LTAI5tG74WCpFJxgkeTHCHpo")
                // 必填，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(("G2rBJChx9U9ORUiky7QdFkF6GGWZKi"));
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    // 发送短信
    public boolean sendToSmsGateway(String phoneNumber, String smsCode, String message) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = createClient();
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setSignName("阿里云短信测试") // 这里替换为你注册的签名
                .setTemplateCode("SMS_154950909") // 替换为你的模板代码
                .setPhoneNumbers(phoneNumber) // 目标手机号
                .setTemplateParam("{\"code\":\"" + smsCode + "\"}");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, runtime);
            return true;
        } catch (TeaException error) {
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 此处仅做打印展示，请谨慎对待异常处理，在工程项目中切勿直接忽略异常。
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return false;
    }

    public Result<Boolean> verifySmsCode(AuthSmsDto authSmsDto) {
        // 1. 获取最近的验证码
        SmsCodeEntity latestCode = smsCodeRepository.findFirstByPhoneNumberOrderByCreatedTimeDesc(authSmsDto.getPhoneNumber());
        if (latestCode == null) {
            // 没有找到验证码记录
            return ResultUtil.wrapFailure(ResultCode.SMSCODE_ERROR, false);
        }

        // 2. 验证码是否匹配
        if (!latestCode.getSmsCode().equals(authSmsDto.getSmsCode())) {
            return ResultUtil.wrapFailure(ResultCode.SMSCODE_ERROR, false); // 验证码不匹配
        }

        // 3. 检查验证码是否过期
        if (latestCode.getExpireTime().isBefore(LocalDateTime.now())) {
            return ResultUtil.wrapFailure(ResultCode.SMSCODE_EXPIRED_ERROR, false); // 验证码已过期
        }

        if(latestCode.getUsed()){
            return ResultUtil.wrapFailure(ResultCode.SMSCODE_USED_ERROR, false);
        }

        latestCode.setUsed(true);
        smsCodeRepository.save(latestCode);
        return ResultUtil.wrapSuccess(true, "验证成功！");
    }
}