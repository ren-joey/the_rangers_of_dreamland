package com.trod.controller;

import com.trod.enums.RoleEnum;
import com.trod.dto.card.MonsterRequestDto;
import com.trod.dto.card.MonsterResponseDto;
import com.trod.dto.main.character.MainCharacterRequestDto;
import com.trod.dto.main.character.MainCharacterResponseDto;
import com.trod.entity.MainCharacter;
import com.trod.entity.Monster;
import com.trod.entity.User;
import com.trod.service.AuthService;
import com.trod.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final AuthService authService;
    private final CardService cardService;
    
    @GetMapping("/monster/{uuid}")
    public MonsterResponseDto getMonster(
        @PathVariable String uuid
    ) {
        Monster monster = cardService.getMonster(uuid);
        return MonsterResponseDto.convert(monster);
    }

    @PostMapping("/monster")
    public MonsterResponseDto addMonsterCard(
        @RequestBody MonsterRequestDto monsterRequestDto
    ) {
        User user = authService.checkPermission(RoleEnum.ADMIN);
        Monster monster = cardService.createMonster(monsterRequestDto, user);
        return MonsterResponseDto.convert(monster);
    }

    @GetMapping("/mainCharacter/{uuid}")
    public MainCharacterResponseDto getMainCharacter(
        @PathVariable String uuid
    ) {
        MainCharacter mainCharacter = cardService.getMainCharacterById(uuid);
        return MainCharacterResponseDto.convert(mainCharacter);
    }

    @PostMapping("/mainCharacter")
    public MainCharacterResponseDto addMainCharacter (
        @RequestBody MainCharacterRequestDto mainCharacterRequestDto
    ) {
        User user = authService.checkPermission(RoleEnum.ADMIN);
        MainCharacter mainCharacter = cardService.createMainCharacter(mainCharacterRequestDto, user);
        return MainCharacterResponseDto.convert(mainCharacter);
    }
}
