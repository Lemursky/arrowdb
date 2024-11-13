package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalConditionENUM {

    OK ("Исправен (готов к выдаче)"),
    DEFECTIVE ("Не исправен (на складе, не в ремонте)"),
    IN_REPAIR ("В ремонте (текущем или гарантийном)"),
    OUT ("Списан (выведен из строя)");

    private final String title;

}