package com.example.arrowdb.services;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.SpecialCloth;
import com.example.arrowdb.entity.SpecialClothEmployee;

import java.util.List;

public interface SpecialClothEmployeeService {

    SpecialClothEmployee findSpecialClothEmployeeById(Integer id);
    List<SpecialClothEmployee> findAllSpecialClothEmployee();
    void saveSpecialClothEmployee (SpecialClothEmployee specialClothEmployee);
    void saveAllSpecialClothEmployee (List <SpecialClothEmployee> specialClothEmployeeList);
    void deleteSpecialClothEmployeeById(Integer id);
    List<SpecialClothEmployee> findAllSpecialClothEmployeeByEmployeeId(Integer id);
    Integer findEmployeeIdBySpecialClothEmployeeId(Integer id);
    List<Integer> findAllEmployeeBySpecialClothEmployeeId(Integer id);
    List<Integer> findSpecialClothByEmployeeId (Integer id);

}
