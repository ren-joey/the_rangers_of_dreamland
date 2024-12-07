package com.trod.entity;

import com.trod.constant.Rarity;
import com.trod.entity.abstractentity.CostUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Monster extends CostUnit {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rarity rarity;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer cost;
}
