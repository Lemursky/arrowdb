package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PersonalConditionENUM {

    ISSUED ("Выдан (на руки работнику)"),
    NOT_ISSUED("Не выдан (на складе)");

    private final String title;

}