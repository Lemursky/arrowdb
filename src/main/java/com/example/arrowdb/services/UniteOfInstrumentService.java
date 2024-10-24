package com.example.arrowdb.services;

import com.example.arrowdb.entity.UniteOfInstrument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UniteOfInstrumentService {
    UniteOfInstrument findUniteOfInstrumentById(Integer id);
    List<UniteOfInstrument> findAllUniteOfInstrument();

}
