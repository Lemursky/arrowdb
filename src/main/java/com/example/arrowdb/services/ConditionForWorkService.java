package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConditionForWork;

import java.util.List;

public interface ConditionForWorkService {
    ConditionForWork findConditionForWorkById(Integer id);
    List<ConditionForWork> findAllConditionForWork();
    ConditionForWork findConditionForWorkBywConditionName(String name);
}
