package com.trod.dto;

import com.trod.constant.RoleEnum;
import com.trod.entity.GameRole;

public record UserResponseDto (
        Long id,
        String username,
        String email,
        RoleEnum role
) {
}
