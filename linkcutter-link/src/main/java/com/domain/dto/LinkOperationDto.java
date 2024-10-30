package com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkOperationDto implements Serializable {

    private Long userId;

    private String operatorName;

    private String originalUrl;

    private LocalDateTime operateTime;

    private String qrCodeId;

    @Override
    public String toString() {
        return "LinkOperationDto{" +
                "userId=" + userId +
                ", originalUrl='" + originalUrl + '\'' +
                ", operateTime=" + operateTime +
                ", qrCodeId='" + qrCodeId + '\'' +
                '}';
    }
}
