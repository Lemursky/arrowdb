package com.example.arrowdb.services;

import com.example.arrowdb.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAllEmployees();
    Employee findEmployeeById(Integer id);
    void saveEmployee(Employee employee);
    void deleteEmployeeById(Integer id);

}
