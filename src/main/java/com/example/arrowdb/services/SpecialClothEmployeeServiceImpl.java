package com.example.arrowdb.services;

import com.example.arrowdb.entity.SpecialClothEmployee;
import com.example.arrowdb.repositories.SpecialClothEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialClothEmployeeServiceImpl implements SpecialClothEmployeeService{

    private final SpecialClothEmployeeRepository specialClothEmployeeRepository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<SpecialClothEmployee> findAllSpecialClothEmployee() {
        return specialClothEmployeeRepository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveSpecialClothEmployee(SpecialClothEmployee specialClothEmployee) {
        specialClothEmployeeRepository.save(specialClothEmployee);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteSpecialClothEmployeeById(Integer id) {
        specialClothEmployeeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<SpecialClothEmployee> findAllSpecialClothEmployeeByEmployeeId(Integer id) {
        return specialClothEmployeeRepository.findAllSpecialClothEmployeeByEmployeeId(id);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Integer findEmployeeIdBySpecialClothEmployeeId(Integer id) {
        return specialClothEmployeeRepository.findEmployeeIdBySpecialClothEmployeeId(id);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Integer> findAllEmployeeBySpecialClothEmployeeId(Integer id) {
        return specialClothEmployeeRepository.findAllEmployeeBySpecialClothEmployeeId(id);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Integer> findSpecialClothByEmployeeId(Integer id) {
        return specialClothEmployeeRepository.findSpecialClothByEmployeeId(id);
    }

}
