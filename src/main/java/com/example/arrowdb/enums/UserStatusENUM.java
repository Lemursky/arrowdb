package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatusENUM {

    ON ("false"),
    OFF ("true");

    private final String title;

    public boolean isStatValue() {
        return this == OFF;
    }
}