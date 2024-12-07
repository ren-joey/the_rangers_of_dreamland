package com.trod.dto;

public record UserResponseDto (
        Long id,
        String username,
        String email
) {
}
