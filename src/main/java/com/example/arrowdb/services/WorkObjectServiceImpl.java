package com.example.arrowdb.services;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.WorkObject;
import com.example.arrowdb.entity.WorkObjectStatus;
import com.example.arrowdb.repositories.WorkObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkObjectServiceImpl implements WorkObjectService {

    private final WorkObjectRepository workObjectRepository;

    @Override
    @Transactional
    public List<WorkObject> findAllWorkObjects() {
        return workObjectRepository.findAll();
    }

    @Override
    @Transactional
    public WorkObject findWorkObjectById(Integer id) {
        WorkObject workObject = null;
        Optional<WorkObject> optional = workObjectRepository.findById(id);
        if (optional.isPresent()) {
            workObject = optional.get();
        }
        return workObject;
    }

    @Override
    @Transactional
    public void saveWorkObject(WorkObject workObject) {
        workObjectRepository.save(workObject);
    }

    @Override
    @Transactional
    public void deleteWorkObjectById(Integer id) {
        workObjectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<WorkObject> findWorkObjectByStatus(Integer status1, Integer status2, Integer status3) {
        return workObjectRepository.findWorkObjectByStatus(status1, status2, status3);
    }
}
