package com.example.arrowdb.services;

import com.example.arrowdb.entity.PersonalInstrument;
import com.example.arrowdb.repositories.PersonalInstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalInstrumentServiceImpl implements PersonalInstrumentService {

    private final PersonalInstrumentRepository personalInstrumentRepository;

    @Override
    @Transactional
    public List<PersonalInstrument> findAllPersonalInstruments() {
        return personalInstrumentRepository.findAll();
    }

    @Override
    @Transactional
    public PersonalInstrument findPersonalInstrumentById(Integer id) {
        PersonalInstrument instruments = null;
        Optional<PersonalInstrument> optional = personalInstrumentRepository.findById(id);
        if (optional.isPresent()){
            instruments = optional.get();
        }
        return instruments;
    }

    @Override
    @Transactional
    public void savePersonalInstrument(PersonalInstrument instruments) {
        personalInstrumentRepository.save(instruments);
    }

    @Override
    @Transactional
    public void deletePersonalInstrumentsById(Integer id) {
        personalInstrumentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Integer> findAllIdEmployees(Integer id) {
        return personalInstrumentRepository.findAllIdEmployees(id);
    }

    @Override
    @Transactional
    public Integer findPersonalInstIdByEmployeeId(Integer id) {
        return personalInstrumentRepository.findPersonalInstIdByEmployeeId(id);
    }

}
