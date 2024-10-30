package com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOperationDto implements Serializable {

    private String title;

    private LocalDateTime createTime;

    private String shortCode;

    private String qrCodeId;

    private String originalUrl;
}
