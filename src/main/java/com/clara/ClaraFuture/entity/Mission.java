package com.clara.ClaraFuture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Missions")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    @ManyToOne
    @JoinColumn(name = "childId", nullable = false)
    private Child child;

    @Column(nullable = false, length = 255)
    private String missionName;

    @Column(nullable = true, length = 255)
    private String description;

    @Column(nullable = false)
    private boolean isCompleted;

    @Column(nullable = false)
    private Integer rewardStar;
}
