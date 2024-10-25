package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConstructionControl;
import com.example.arrowdb.entity.WorkObject;
import com.example.arrowdb.entity.WorkObjectStatus;

import java.util.List;

public interface ConstructionControlService {
    ConstructionControl findConstructionControlById(Integer id);
    List<ConstructionControl> findAllConstructionControl();
    void saveConstructionControl(ConstructionControl constructionControl);
    void deleteConstructionControlById(Integer id);

}
