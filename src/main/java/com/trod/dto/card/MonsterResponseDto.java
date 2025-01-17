package com.trod.dto.card;

import com.trod.constant.RarityEnum;
import com.trod.entity.Monster;

public record MonsterResponseDto (
        String id,
        RarityEnum rarity,
        String name,
        String description,
        Integer cost,
        Integer health,
        Integer mana,
        Integer attack,
        Integer defense
) {
    public static MonsterResponseDto convert(Monster monster) {
        return new MonsterResponseDto(
            monster.getId(),
            monster.getRarity(),
            monster.getName(),
            monster.getDescription(),
            monster.getCost(),
            monster.getHealth(),
            monster.getMana(),
            monster.getAttack(),
            monster.getDefense()
        );
    }
}
