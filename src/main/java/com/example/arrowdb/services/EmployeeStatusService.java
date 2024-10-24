package com.example.arrowdb.services;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.EmployeeStatus;

import java.util.List;

public interface EmployeeStatusService {
    EmployeeStatus findEmployeeStatusByStatusName(String name);
    EmployeeStatus findEmployeeStatusById(Integer id);
    List<EmployeeStatus> findAllEmployeeStatus();

}
