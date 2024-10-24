package com.example.arrowdb.services;

import com.example.arrowdb.entity.WorkInstrument;
import com.example.arrowdb.repositories.WorkInstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkInstrumentServiceImpl implements WorkInstrumentService{

    private final WorkInstrumentRepository workInstrumentRepository;

    @Override
    @Transactional
    public List<WorkInstrument> findAllWorkInstruments() {
        return workInstrumentRepository.findAll();
    }

    @Override
    @Transactional
    public WorkInstrument findWorkInstrumentById(Integer id) {
        WorkInstrument workInstrument = null;
        Optional<WorkInstrument> optional = workInstrumentRepository.findById(id);
        if(optional.isPresent()){
            workInstrument = optional.get();
        }
        return workInstrument;
    }

    @Override
    @Transactional
    public void saveWorkInstrument(WorkInstrument instruments) {
        workInstrumentRepository.save(instruments);
    }

    @Override
    public void saveAllWorkInstrument(List<WorkInstrument> workInstrumentList) {
        workInstrumentRepository.saveAll(workInstrumentList);
    }

    @Override
    @Transactional
    public void deleteWorkInstrumentsById(Integer id) {
        workInstrumentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Integer> findAllIdEmployees(Integer id) {
        return workInstrumentRepository.findAllIdEmployees(id);
    }

    @Override
    @Transactional
    public List<Integer> findAllWorkInstrumentByWorkObjectId(Integer id) {
        return workInstrumentRepository.findAllWorkInstrumentByWorkObjectId(id);
    }

    @Override
    @Transactional
    public Integer findEmployeeIdByWorkInstId(Integer id) {
        return workInstrumentRepository.findEmployeeIdByWorkInstId(id);
    }

    @Override
    @Transactional
    public List<WorkInstrument> findAllWorkInstrumentById(Integer id) {
        return workInstrumentRepository.findAllWorkInstrumentById(id);
    }

}
