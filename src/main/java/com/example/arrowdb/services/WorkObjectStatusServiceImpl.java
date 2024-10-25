package com.example.arrowdb.services;

import com.example.arrowdb.entity.WorkObjectStatus;
import com.example.arrowdb.repositories.WorkObjectStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkObjectStatusServiceImpl implements WorkObjectStatusService {

    private final WorkObjectStatusRepository workObjectStatusRepository;

    @Override
    @Transactional
    public WorkObjectStatus findWorkObjectStatusByStatusName(String name) {
        return workObjectStatusRepository.findWorkObjectStatusByStatusName(name);
    }

    @Override
    @Transactional
    public WorkObjectStatus findWorkObjectStatusBiId(Integer id) {
        WorkObjectStatus workObjectStatus = null;
        Optional<WorkObjectStatus> optional = workObjectStatusRepository.findById(id);
        if (optional.isPresent()) {
            workObjectStatus = optional.get();
        }
        return workObjectStatus;
    }

    @Override
    @Transactional
    public List<WorkObjectStatus> findAllWorkObjectStatus() {
        return workObjectStatusRepository.findAll();
    }

    @Override
    @Transactional
    public Integer findWorkObjectStatusIdByStatusName(String name) {
        return workObjectStatusRepository.findWorkObjectStatusIdByStatusName(name);
    }

}
