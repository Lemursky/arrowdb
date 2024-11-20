package com.example.arrowdb.services;

import com.example.arrowdb.entity.Department;
import com.example.arrowdb.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Department findDepartmentById(Integer id) {
        Department department = null;
        Optional<Department> optional = departmentRepository.findById(id);
        if (optional.isPresent()) {
            department = optional.get();
        }
        return department;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }
}
