package com.trod.constant;

import java.util.EnumMap;
import java.util.Map;

public class RarityMapper {
    private static final Map<Rarity, RarityAbbreviation> rarityToRarityAbbreviationMapping = new EnumMap<>(Rarity.class);
    private static final Map<RarityAbbreviation, Rarity> rarityAbbreviationToRarityMapping = new EnumMap<>(RarityAbbreviation.class);

    static {
        rarityToRarityAbbreviationMapping.put(Rarity.NORMAL, RarityAbbreviation.N);
        rarityToRarityAbbreviationMapping.put(Rarity.RARE, RarityAbbreviation.R);
        rarityToRarityAbbreviationMapping.put(Rarity.SUPER_RARE, RarityAbbreviation.SR);
        rarityToRarityAbbreviationMapping.put(Rarity.EPIC, RarityAbbreviation.SSR);
        rarityToRarityAbbreviationMapping.put(Rarity.LEGENDARY, RarityAbbreviation.UR);
        rarityAbbreviationToRarityMapping.put(RarityAbbreviation.N, Rarity.NORMAL);
        rarityAbbreviationToRarityMapping.put(RarityAbbreviation.R, Rarity.RARE);
        rarityAbbreviationToRarityMapping.put(RarityAbbreviation.SR, Rarity.SUPER_RARE);
        rarityAbbreviationToRarityMapping.put(RarityAbbreviation.SSR, Rarity.EPIC);
        rarityAbbreviationToRarityMapping.put(RarityAbbreviation.UR, Rarity.LEGENDARY);
    }

    public static RarityAbbreviation getRarityAbbreviation(Rarity rarity) {
        return rarityToRarityAbbreviationMapping.get(rarity);
    }

    public static Rarity getRarity(RarityAbbreviation rarityAbbreviation) {
        return rarityAbbreviationToRarityMapping.get(rarityAbbreviation);
    }
}
