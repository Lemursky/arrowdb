package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConditionForTechn;
import com.example.arrowdb.repositories.ConditionForTechnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConditionForTechnServiceImpl implements ConditionForTechnService{

    private final ConditionForTechnRepository conditionForTechnRepository;

    @Override
    @Transactional
    public ConditionForTechn findConditionForTechnById(Integer id) {
        ConditionForTechn conditionForTechn = null;
        Optional<ConditionForTechn> optional = conditionForTechnRepository.findById(id);
        if (optional.isPresent()){
            conditionForTechn = optional.get();
        }
        return conditionForTechn;
    }

    @Override
    @Transactional
    public List<ConditionForTechn> findAllConditionForTechn() {
        return conditionForTechnRepository.findAll();
    }

    @Override
    @Transactional
    public ConditionForTechn findConditionForTechnBytConditionName(String name) {
        return conditionForTechnRepository.findConditionForTechnBytConditionName(name);
    }
}
