package com.example.arrowdb.services;

import com.example.arrowdb.entity.MeasInstrument;
import com.example.arrowdb.repositories.MeasInstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeasInstrumentServiceImpl implements MeasInstrumentService {

    private final MeasInstrumentRepository measInstrumentRepository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public MeasInstrument findMeasInstrumentById(Integer id) {
        MeasInstrument measInstrument = null;
        Optional<MeasInstrument> optional = measInstrumentRepository.findById(id);
        if (optional.isPresent()) {
            measInstrument = optional.get();
        }
        return measInstrument;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveMeasInstrument(MeasInstrument measInstrument) {
        measInstrumentRepository.save(measInstrument);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteMeasInstrumentById(Integer id) {
        measInstrumentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<MeasInstrument> findAllMeasInstruments() {
        return measInstrumentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Integer findEmployeeIdByMeasInstId(Integer id) {
        return measInstrumentRepository.findEmployeeIdByMeasInstId(id);
    }

}