package com.trod.mapper;

import com.trod.entity.MainCharacter;
import com.trod.entity.Monster;

public interface CardMapper {
    Monster getMonster(String uuid);
    MainCharacter getMainCharacter(String uuid);
    void insertMonsterCard(Monster monster);
    void insertMainCharacter(MainCharacter mainCharacter);
}
