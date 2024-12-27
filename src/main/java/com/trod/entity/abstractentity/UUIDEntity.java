package com.trod.entity.abstractentity;

import com.trod.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class UUIDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Long createdTime;

    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @Column()
    private Long updatedTime;

    @ManyToOne
    @JoinColumn(name = "updated_user_id")
    private User updatedUser;
}
