package com.example.arrowdb.services;

import com.example.arrowdb.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department findDepartmentById(Integer id);
    List<Department> findAllDepartments();
}
