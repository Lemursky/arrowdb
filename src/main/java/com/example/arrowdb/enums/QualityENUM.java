package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QualityENUM {

    FIRST ("Среднее профессиональное образование"),
    SECOND ("Высшее образование - бакалавриат"),
    THIRD ("Высшее образование - специалитет, магистратура"),
    FOURTH ("Высшее образование - подготовка кадров высшей квалификации");

    private final String title;

}