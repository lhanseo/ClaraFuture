package com.clara.ClaraFuture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @ManyToOne
    @JoinColumn(name = "childId", nullable = false)
    private Child child;

    @Column(nullable = false, precision = 9, scale = 6)
    private Double latitude;

    @Column(nullable = false, precision = 9, scale = 6)
    private Double longitude;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;
}
