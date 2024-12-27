package com.trod.controller;

import com.trod.constant.RoleEnum;
import com.trod.dto.card.MonsterRequestDto;
import com.trod.dto.card.MonsterResponseDto;
import com.trod.entity.Monster;
import com.trod.entity.User;
import com.trod.mapper.CardMapper;
import com.trod.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final AuthService authService;
    private final CardMapper cardMapper;

    @PostMapping("/add/monster")
    public MonsterResponseDto addMonsterCard(
        @RequestBody MonsterRequestDto monsterRequestDto
    ) {
        User user = authService.checkPermission(RoleEnum.ADMIN);

        Monster monster = new Monster();
        monster.setName(monsterRequestDto.name());
        monster.setRarityEnum(monsterRequestDto.rarityEnum());
        monster.setDescription(monsterRequestDto.description());
        monster.setCost(monsterRequestDto.cost());
        monster.setHealth(monsterRequestDto.health());
        monster.setMana(monsterRequestDto.mana());
        monster.setAttack(monsterRequestDto.attack());
        monster.setDefense(monsterRequestDto.defense());
        monster.setCreatedUser(user);
        monster.setCreatedTime(System.currentTimeMillis());
        cardMapper.insertMonsterCard(monster);

        return MonsterResponseDto.convertToMonsterResponseDto(monster);
    }
}
