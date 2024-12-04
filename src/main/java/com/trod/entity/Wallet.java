package com.trod.entity;

import com.trod.entity.abstractentity.IdentityEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Wallet extends IdentityEntity {
    @Column(columnDefinition = "integer default 0")
    private Integer gifted_points;

    @Column(columnDefinition = "integer default 0")
    private Integer paid_points;

    @Column(columnDefinition = "integer default 0")
    private Integer in_game_points;

    private Integer total_points = gifted_points + paid_points;
}
