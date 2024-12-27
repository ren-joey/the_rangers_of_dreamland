package com.trod.entity.abstractentity;

import com.trod.constant.RarityEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Unit extends UUIDEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RarityEnum rarityEnum;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;
}
