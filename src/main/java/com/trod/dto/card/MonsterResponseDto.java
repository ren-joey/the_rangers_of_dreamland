package com.trod.dto.card;

import com.trod.constant.RarityEnum;
import com.trod.entity.Monster;

public record MonsterResponseDto (
        String id,
        RarityEnum rarityEnum,
        String name,
        String description,
        Integer cost,
        Integer health,
        Integer mana,
        Integer attack,
        Integer defense
) {
    public static MonsterResponseDto convertToMonsterResponseDto(Monster monster) {
        return new MonsterResponseDto(
            monster.getId(),
            monster.getRarityEnum(),
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
