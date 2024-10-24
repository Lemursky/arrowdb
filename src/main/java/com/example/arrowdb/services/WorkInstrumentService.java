package com.example.arrowdb.services;

import com.example.arrowdb.entity.WorkInstrument;

import java.util.List;

public interface WorkInstrumentService {

    List<WorkInstrument> findAllWorkInstruments();
    WorkInstrument findWorkInstrumentById(Integer id);
    void saveWorkInstrument(WorkInstrument instruments);
    void saveAllWorkInstrument(List<WorkInstrument> workInstrumentList);
    void deleteWorkInstrumentsById(Integer id);
    List<Integer> findAllIdEmployees(Integer id);
    List<Integer> findAllWorkInstrumentByWorkObjectId(Integer id);
    Integer findEmployeeIdByWorkInstId(Integer id);
    List<WorkInstrument> findAllWorkInstrumentById(Integer id);

}
