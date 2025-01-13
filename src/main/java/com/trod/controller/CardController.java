package com.trod.controller;

import com.trod.constant.RoleEnum;
import com.trod.dto.card.MonsterRequestDto;
import com.trod.dto.card.MonsterResponseDto;
import com.trod.entity.Monster;
import com.trod.entity.User;
import com.trod.service.AuthService;
import com.trod.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final AuthService authService;
    private final CardService cardService;

    @PostMapping("/add/monster")
    public MonsterResponseDto addMonsterCard(
        @RequestBody MonsterRequestDto monsterRequestDto
    ) {
        User user = authService.checkPermission(RoleEnum.ADMIN);
        Monster monster = cardService.createMonster(monsterRequestDto, user);
        return MonsterResponseDto.convertToMonsterResponseDto(monster);
    }
}
