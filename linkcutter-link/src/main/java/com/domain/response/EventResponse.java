package com.domain.response;

import com.domain.dto.LinkOperationDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EventResponse implements Serializable {

    private String shortLink;

    private LocalDateTime createTime;

    private String title;

    private LinkOperationDto linkOperationDto;
}
