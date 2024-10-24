package com.example.arrowdb.services;

import com.example.arrowdb.entity.MeasInstrument;
import com.example.arrowdb.entity.WorkInstrument;

import java.time.LocalDate;
import java.util.List;

public interface MeasInstrumentService {
    MeasInstrument findMeasInstrumentById(Integer id);
    List<Integer> findAllIdEmployees(Integer id);
    void saveMeasInstrument(MeasInstrument measInstrument);
    void deleteMeasInstrumentById(Integer id);
    void saveAllMeasInstrument(List<MeasInstrument> measInstrumentList);
    List<MeasInstrument> findAllMeasInstruments();
    List<Integer> findAllMeasInstrumentByWorkObjectId(Integer id);
    List<MeasInstrument> findAllMeasInstrumentById(List<Integer> id);
    Integer findEmployeeIdByMeasInstId(Integer id);
    List<MeasInstrument> findAllMeasInstrumentById(Integer id);

}
