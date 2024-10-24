package com.example.arrowdb.services;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.SpecialCloth;
import com.example.arrowdb.entity.SpecialClothEmployee;
import com.example.arrowdb.repositories.SpecialClothEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialClothEmployeeServiceImpl implements SpecialClothEmployeeService{

    private final SpecialClothEmployeeRepository specialClothEmployeeRepository;

    @Override
    @Transactional
    public SpecialClothEmployee findSpecialClothEmployeeById(Integer id) {
        SpecialClothEmployee specialClothEmployee = null;
        Optional<SpecialClothEmployee> optional = specialClothEmployeeRepository.findById(id);
        if(optional.isPresent()){
            specialClothEmployee = optional.get();
        }
        return specialClothEmployee;
    }

    @Override
    @Transactional
    public List<SpecialClothEmployee> findAllSpecialClothEmployee() {
        return specialClothEmployeeRepository.findAll();
    }

    @Override
    @Transactional
    public void saveSpecialClothEmployee(SpecialClothEmployee specialClothEmployee) {
        specialClothEmployeeRepository.save(specialClothEmployee);
    }

    @Override
    @Transactional
    public void saveAllSpecialClothEmployee(List<SpecialClothEmployee> specialClothEmployeeList) {
        specialClothEmployeeRepository.saveAll(specialClothEmployeeList);
    }

    @Override
    @Transactional
    public void deleteSpecialClothEmployeeById(Integer id) {
        specialClothEmployeeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<SpecialClothEmployee> findAllSpecialClothEmployeeByEmployeeId(Integer id) {
        return specialClothEmployeeRepository.findAllSpecialClothEmployeeByEmployeeId(id);
    }

    @Override
    @Transactional
    public Integer findEmployeeIdBySpecialClothEmployeeId(Integer id) {
        return specialClothEmployeeRepository.findEmployeeIdBySpecialClothEmployeeId(id);
    }

    @Override
    @Transactional
    public List<Integer> findAllEmployeeBySpecialClothEmployeeId(Integer id) {
        return specialClothEmployeeRepository.findAllEmployeeBySpecialClothEmployeeId(id);
    }

    @Override
    @Transactional
    public List<Integer> findSpecialClothByEmployeeId(Integer id) {
        return specialClothEmployeeRepository.findSpecialClothByEmployeeId(id);
    }

}
