package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConditionForPersonal;
import com.example.arrowdb.entity.ConditionForTechn;

import java.util.List;

public interface ConditionForTechnService {
    ConditionForTechn findConditionForTechnById(Integer id);
    List<ConditionForTechn> findAllConditionForTechn();
    ConditionForTechn findConditionForTechnBytConditionName(String name);

}