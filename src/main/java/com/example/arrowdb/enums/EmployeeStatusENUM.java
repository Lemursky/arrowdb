package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmployeeStatusENUM {
    ACTIVE ("Действующий"),
    IN_VOCATION ("В отпуске"),
    CLOTHED ("Закрыт");

    private final String title;

}