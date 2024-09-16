package com.clara.ClaraFuture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Star")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long starId;

    @ManyToOne
    @JoinColumn(name = "childId", nullable = false)
    private Child child;

    @Column(nullable = false)
    private Integer starCount;
}
