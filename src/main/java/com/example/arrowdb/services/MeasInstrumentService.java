package com.example.arrowdb.services;

import com.example.arrowdb.entity.MeasInstrument;

import java.util.List;

public interface MeasInstrumentService {
    MeasInstrument findMeasInstrumentById(Integer id);
    void saveMeasInstrument(MeasInstrument measInstrument);
    void deleteMeasInstrumentById(Integer id);
    List<MeasInstrument> findAllMeasInstruments();
    Integer findEmployeeIdByMeasInstId(Integer id);

}
