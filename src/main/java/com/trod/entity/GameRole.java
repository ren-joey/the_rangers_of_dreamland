package com.trod.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GameRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.trod.constant.Role role;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
