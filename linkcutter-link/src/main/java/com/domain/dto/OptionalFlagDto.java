package com.domain.dto;

import com.constant.OptionalFlagConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalFlagDto implements Serializable {
    private Integer qrCodeFlag = OptionalFlagConstant.ENABLE;

    private Integer pageFlag = OptionalFlagConstant.ENABLE;

    private Integer customFlag = OptionalFlagConstant.ENABLE;

    private Integer titleFlag = OptionalFlagConstant.ENABLE;

}
