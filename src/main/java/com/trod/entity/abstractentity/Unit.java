package com.trod.entity.abstractentity;

import com.trod.enums.RarityEnum;
import com.trod.enums.ThemeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Unit extends UUIDEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RarityEnum rarity = RarityEnum.NORMAL;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThemeEnum theme = ThemeEnum.VANILLA;
}
