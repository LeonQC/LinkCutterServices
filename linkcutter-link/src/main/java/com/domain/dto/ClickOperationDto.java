package com.domain.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.AbstractCollection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClickOperationDto implements Serializable {

    private Long eventId;

    private String operatorName;

    private Long operatorId;

    private Integer eventType;
}
