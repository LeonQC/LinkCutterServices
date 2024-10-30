package com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkPaginationDto {

    private String shortCode;

    private String originalUrl;

    private LocalDateTime createTime;

    private String title;

    private String operatorName;

    private String qrCodeId;
}
