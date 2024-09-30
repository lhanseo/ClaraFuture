package com.clara.ClaraFuture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "OAuthInfo")
public class OAuthInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oauthId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Parent parent;

    @Column(name = "roles", nullable = true, length = 50)
    private String roles;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 50)
    private String provider;

    @Column(nullable = false, length = 255)
    private String providerId;
}
