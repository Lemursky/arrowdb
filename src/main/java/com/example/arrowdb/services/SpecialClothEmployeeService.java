package com.example.arrowdb.services;

import com.example.arrowdb.entity.SpecialClothEmployee;

import java.util.List;

public interface SpecialClothEmployeeService {

    List<SpecialClothEmployee> findAllSpecialClothEmployee();
    void saveSpecialClothEmployee (SpecialClothEmployee specialClothEmployee);
    void deleteSpecialClothEmployeeById(Integer id);
    List<SpecialClothEmployee> findAllSpecialClothEmployeeByEmployeeId(Integer id);
    Integer findEmployeeIdBySpecialClothEmployeeId(Integer id);
    List<Integer> findAllEmployeeBySpecialClothEmployeeId(Integer id);
    List<Integer> findSpecialClothByEmployeeId (Integer id);

}
