package com.example.arrowdb.services;

import com.example.arrowdb.entity.Profession;
import com.example.arrowdb.repositories.ProfessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessionServiceImpl implements ProfessionService{

    private final ProfessionRepository professionRepository;

    @Override
    @Transactional
    public List<Profession> findAllProfessions() {
        return professionRepository.findAll();
    }

    @Override
    @Transactional
    public Profession findProfessionById(Integer id) {
        Profession profession = null;
        Optional<Profession> optional = professionRepository.findById(id);
        if(optional.isPresent()){
            profession = optional.get();
        }
        return profession;
    }

    @Override
    @Transactional
    public void saveProfession(Profession profession) {
        professionRepository.save(profession);
    }

    @Override
    @Transactional
    public void deleteProfessionById(Integer id) {
        professionRepository.deleteById(id);
    }
}
