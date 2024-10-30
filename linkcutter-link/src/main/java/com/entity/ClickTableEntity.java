package com.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "click_table")
public class ClickTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "click_table_id_seq")
    @SequenceGenerator(name = "click_table_id_seq", sequenceName = "click_table_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "click_time")
    private LocalDateTime clickTime;

    @Column(name = "ip_address", length = 4)
    private String ipAddress;

    @Column(name = "referrer", length = 5)
    private String referrer;

    @Column(name = "user_agent", length = 6)
    private String userAgent;

    @Column(name = "operator_name", length = 7)
    private String operatorName;

    @Column(name = "operator_id", nullable = false)
    private Long operatorId;  // 去掉 @GeneratedValue 注解

    @Column(name = "click_count", nullable = false)
    private Integer clickCount;

    @Column(name = "event_type", nullable = false)
    private Integer eventType;
}