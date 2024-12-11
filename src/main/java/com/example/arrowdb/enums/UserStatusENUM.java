package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatusENUM {

    ON ("Действует"),
    OFF ("Отключен");

    private final String title;

    public boolean isStatValue() {
        return this == OFF;
    }
}