package com.trod.dto.main.character;

import com.trod.constant.RarityEnum;
import com.trod.entity.MainCharacter;

public record MainCharacterResponseDto (
        String id,
        String name,
        String description,
        RarityEnum rarity,
        Integer cost,
        Integer health,
        Integer mana
)  {
    public static MainCharacterResponseDto convert(MainCharacter mainCharacter) {
        return new MainCharacterResponseDto(
            mainCharacter.getId(),
            mainCharacter.getName(),
            mainCharacter.getDescription(),
            mainCharacter.getRarity(),
            mainCharacter.getCost(),
            mainCharacter.getHealth(),
            mainCharacter.getMana()
        );
    }
}
