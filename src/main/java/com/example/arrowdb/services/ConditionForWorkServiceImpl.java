package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConditionForWork;
import com.example.arrowdb.repositories.ConditionForWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConditionForWorkServiceImpl implements ConditionForWorkService{

    private final ConditionForWorkRepository conditionForWorkRepository;

    @Override
    @Transactional
    public ConditionForWork findConditionForWorkById(Integer id) {
        ConditionForWork conditionForWork = null;
        Optional<ConditionForWork> optional = conditionForWorkRepository.findById(id);
        if(optional.isPresent()){
            conditionForWork = optional.get();
        }
        return conditionForWork;
    }

    @Override
    @Transactional
    public List<ConditionForWork> findAllConditionForWork() {
        return conditionForWorkRepository.findAll();
    }

    @Override
    @Transactional
    public ConditionForWork findConditionForWorkBywConditionName(String name) {
        return conditionForWorkRepository.findConditionForWorkBywConditionName(name);
    }
}
