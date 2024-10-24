package com.example.arrowdb.services;

import com.example.arrowdb.entity.EmployeeStatus;
import com.example.arrowdb.repositories.EmployeeStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeStatusServiceImpl implements EmployeeStatusService {

    private final EmployeeStatusRepository employeeStatusRepository;

    @Override
    @Transactional
    public EmployeeStatus findEmployeeStatusByStatusName(String name) {
        return employeeStatusRepository.findEmployeeStatusByStatusName(name);
    }

    @Override
    @Transactional
    public EmployeeStatus findEmployeeStatusById(Integer id) {
        EmployeeStatus employeeStatus = null;
        Optional<EmployeeStatus> optional = employeeStatusRepository.findById(id);
        if(optional.isPresent()){
            employeeStatus = optional.get();
        }
        return employeeStatus;
    }

    @Override
    @Transactional
    public List<EmployeeStatus> findAllEmployeeStatus() {
        return employeeStatusRepository.findAll();
    }
}
