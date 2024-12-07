package com.trod.entity;

import com.trod.entity.abstractentity.CostUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Monster extends CostUnit {
    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer cost;
}
