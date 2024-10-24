package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConditionForPersonal;
import com.example.arrowdb.repositories.ConditionForPersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConditionForPersonalServiceImpl implements ConditionForPersonalService {

    private final ConditionForPersonalRepository conditionForEmployeeRepository;

    @Override
    @Transactional
    public ConditionForPersonal findConditionForPersonalById(Integer id) {
        ConditionForPersonal conditionForEmployee = null;
        Optional<ConditionForPersonal> optional = conditionForEmployeeRepository.findById(id);
        if(optional.isPresent()){
            conditionForEmployee = optional.get();
        }
        return conditionForEmployee;
    }

    @Override
    @Transactional
    public List<ConditionForPersonal> findAllConditionForPersonal() {
        return conditionForEmployeeRepository.findAll();
    }

    @Override
    @Transactional
    public ConditionForPersonal findConditionForPersonalBypConditionName(String name) {
        return conditionForEmployeeRepository.findConditionForPersonalBypConditionName(name);
    }
}