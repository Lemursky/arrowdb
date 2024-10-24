package com.example.arrowdb.services;

import com.example.arrowdb.entity.UniteOfInstrument;
import com.example.arrowdb.repositories.UniteOfInstrumentRepository;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniteOfInstrumentServiceImpl implements UniteOfInstrumentService{

    private final UniteOfInstrumentRepository uniteOfInstrumentRepository;

    @Override
    @Transactional
    public UniteOfInstrument findUniteOfInstrumentById(Integer id) {
        UniteOfInstrument uniteOfInstrument = null;
        Optional<UniteOfInstrument> optional = uniteOfInstrumentRepository.findById(id);
        if(optional.isPresent()){
            uniteOfInstrument = optional.get();
        }
        return uniteOfInstrument;
    }

    @Override
    @Transactional
    public List<UniteOfInstrument> findAllUniteOfInstrument() {
        return uniteOfInstrumentRepository.findAll();
    }
}
