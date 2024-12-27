package com.trod.mapper;

import com.trod.entity.Monster;
import org.apache.ibatis.annotations.Insert;

public interface CardMapper {
    @Insert("INSERT INTO monster(name, attack, defense, level, type, attribute, description) VALUES(#{name}, #{attack}, #{defense}, #{level}, #{type}, #{attribute}, #{description})")
    void insertMonsterCard(Monster monster);
}
