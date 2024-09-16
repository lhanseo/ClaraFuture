package com.clara.ClaraFuture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "DeviceManagement")
public class DeviceManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managementId;

    @ManyToOne
    @JoinColumn(name = "deviceId", nullable = false)
    private Device device;

    @Column(nullable = false, length = 50)
    private String action;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime actionTime;
}
