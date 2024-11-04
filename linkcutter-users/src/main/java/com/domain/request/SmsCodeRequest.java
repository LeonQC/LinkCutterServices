package com.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCodeRequest implements Serializable {

    private String phoneNumber;

    private String startNumber;

    private Integer scene;
}
