package com.trod.enums;

public enum ThemeEnum {
    VANILLA("vanilla");

    private final String name;

    ThemeEnum(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static ThemeEnum fromString(String str) {
        return fromValue(str);
    }

    public static ThemeEnum fromValue(String index) {
        for (ThemeEnum e : values()) {
            if (e.toString().equals(index)) {
                return e;
            }
        }
        return null;
    }
}
