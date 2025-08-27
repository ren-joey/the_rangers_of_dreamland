package com.trod.dto.main.character;

import com.trod.enums.RarityEnum;
import com.trod.enums.ThemeEnum;

public record MainCharacterRequestDto (
        String name,
        String description,
        RarityEnum rarity,
        Integer cost,
        Integer health,
        Integer mana,
        ThemeEnum theme
) {
}
