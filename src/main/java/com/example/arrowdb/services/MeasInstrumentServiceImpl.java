package com.example.arrowdb.services;

import com.example.arrowdb.entity.MeasInstrument;
import com.example.arrowdb.repositories.MeasInstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeasInstrumentServiceImpl implements MeasInstrumentService {

    private final MeasInstrumentRepository measInstrumentRepository;

    @Override
    @Transactional(readOnly = true)
    public MeasInstrument findMeasInstrumentById(Integer id) {
        MeasInstrument measInstrument = null;
        Optional<MeasInstrument> optional = measInstrumentRepository.findById(id);
        if (optional.isPresent()) {
            measInstrument = optional.get();
        }
        return measInstrument;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> findAllIdEmployees(Integer id) {
        return measInstrumentRepository.findAllIdEmployees(id);
    }

    @Override
    @Transactional
    public void saveMeasInstrument(MeasInstrument measInstrument) {
        measInstrumentRepository.save(measInstrument);
    }

    @Override
    @Transactional
    public void deleteMeasInstrumentById(Integer id) {
        measInstrumentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveAllMeasInstrument(List<MeasInstrument> measInstrumentList) {
        measInstrumentRepository.saveAll(measInstrumentList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasInstrument> findAllMeasInstruments() {
        return measInstrumentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> findAllMeasInstrumentByWorkObjectId(Integer id) {
        return measInstrumentRepository.findAllMeasInstrumentByWorkObjectId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasInstrument> findAllMeasInstrumentById(List<Integer> id) {
        return measInstrumentRepository.findAllById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findEmployeeIdByMeasInstId(Integer id) {
        return measInstrumentRepository.findEmployeeIdByMeasInstId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasInstrument> findAllMeasInstrumentById(Integer id) {
        return measInstrumentRepository.findAllMeasInstrumentById(id);
    }

}
