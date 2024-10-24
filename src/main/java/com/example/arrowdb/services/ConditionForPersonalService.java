package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConditionForPersonal;

import java.util.List;

public interface ConditionForPersonalService {
    ConditionForPersonal findConditionForPersonalById(Integer id);
    List<ConditionForPersonal> findAllConditionForPersonal();
    ConditionForPersonal findConditionForPersonalBypConditionName(String name);
}
