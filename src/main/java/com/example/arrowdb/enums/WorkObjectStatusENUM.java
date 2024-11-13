package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkObjectStatusENUM {

    NOT_STARTED ("Не начат"),
    ACTIVE ("Действующий"),
    TERMINATED ("Приостановлен"),
    CLOTHED ("Закрыт");

    private final String title;

}