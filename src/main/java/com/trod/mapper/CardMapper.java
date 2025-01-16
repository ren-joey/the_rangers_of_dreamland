package com.trod.mapper;

import com.trod.entity.Monster;
import org.apache.ibatis.annotations.Insert;

public interface CardMapper {
    void insertMonsterCard(Monster monster);
}
