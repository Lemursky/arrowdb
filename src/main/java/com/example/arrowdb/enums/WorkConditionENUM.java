package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkConditionENUM {

    NOT_INVOLVED ("Не закреплен (на складе г. Пермь)"),
    INVOLVED ("Закреплен (за объектом строительства)");

    private final String title;

}