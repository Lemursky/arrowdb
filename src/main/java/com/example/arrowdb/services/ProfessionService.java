package com.example.arrowdb.services;

import com.example.arrowdb.entity.Profession;

import java.util.List;

public interface ProfessionService {
    List<Profession> findAllProfessions();
    Profession findProfessionById(Integer id);
    void saveProfession(Profession profession);
    void deleteProfessionById(Integer id);
}
