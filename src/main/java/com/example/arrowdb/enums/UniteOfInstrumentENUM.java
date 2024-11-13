package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UniteOfInstrumentENUM {

    THING ("шт"),
    SET ("компл.");

    private final String title;

}
