package com.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "sms.code")
public class SmsCodeProperties implements Serializable {

    private Duration expireTime;

    private Duration sendFrequency;

    private Integer sendMaximumPerDay;

    private Integer beginCode;

    private Integer endCode;
}
