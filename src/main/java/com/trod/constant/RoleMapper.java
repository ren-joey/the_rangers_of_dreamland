package com.trod.constant;

public class RoleMapper {
    public static Role numberToPermission(int number) {
        return switch (number) {
            case 0 -> Role.PLAYER;
            case 1 -> Role.GAME_MASTER;
            case 2 -> Role.ADMIN;
            default -> null;
        };
    }

    public static int permissionToNumber(Role permission) {
        return switch (permission) {
            case PLAYER -> 0;
            case GAME_MASTER -> 1;
            case ADMIN -> 2;
        };
    }
}
