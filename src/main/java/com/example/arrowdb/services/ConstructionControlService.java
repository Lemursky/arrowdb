package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConstructionControl;

import java.util.List;

public interface ConstructionControlService {
    ConstructionControl findConstructionControlById(Integer id);
    List<ConstructionControl> findAllConstructionControl();
    void saveConstructionControl(ConstructionControl constructionControl);
    void deleteConstructionControlById(Integer id);

}