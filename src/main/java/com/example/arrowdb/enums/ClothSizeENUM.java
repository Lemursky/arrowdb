package com.example.arrowdb.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClothSizeENUM {

    XXS ("XS / 42-44"),
    XS ("XS / 44-46"),
    S ("S / 46-48"),
    M ("M / 48-50"),
    L ("L / 50-52"),
    XL ("XL / 52-54"),
    XXL ("XXL / 54-56"),
    XXXL ("XXXL / 56-68");

    private final String title;

}