package com.example.arrowdb.services;

import com.example.arrowdb.entity.WorkObject;
import com.example.arrowdb.repositories.WorkObjectRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkObjectServiceImpl implements WorkObjectService {

    private final WorkObjectRepository workObjectRepository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<WorkObject> findAllWorkObjects() {
        return workObjectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public WorkObject findWorkObjectById(Integer id) {
        WorkObject workObject = null;
        Optional<WorkObject> optional = workObjectRepository.findById(id);
        if (optional.isPresent()) {
            workObject = optional.get();
        }
        return workObject;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveWorkObject(WorkObject workObject) {
        workObjectRepository.save(workObject);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteWorkObjectById(Integer id) {
        workObjectRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<WorkObject> findWorkObjectByStatus(Integer status1, Integer status2, Integer status3) {
        return workObjectRepository.findWorkObjectByStatus(status1, status2, status3);
    }

}
