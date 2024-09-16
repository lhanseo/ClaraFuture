package com.clara.ClaraFuture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usage_time_restrictions")
public class UsageTimeRestrictions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restrictionId;

    @Column(nullable = false)
    private int dailyLimitMinutes;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "deviceId", nullable = false)
    private Device device;
}
