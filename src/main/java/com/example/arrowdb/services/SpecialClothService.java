package com.example.arrowdb.services;

import com.example.arrowdb.entity.SpecialCloth;

import java.util.List;

public interface SpecialClothService {
    List<SpecialCloth> findAllSpecialCloths();
    SpecialCloth findSpecialClothById(Integer id);
    void saveSpecialCloth(SpecialCloth specialCloth);
    void deleteSpecialClothById(Integer id);

}
