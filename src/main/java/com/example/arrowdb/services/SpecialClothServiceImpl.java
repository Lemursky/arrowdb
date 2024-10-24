package com.example.arrowdb.services;

import com.example.arrowdb.entity.SpecialCloth;
import com.example.arrowdb.repositories.SpecialClothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialClothServiceImpl implements SpecialClothService{

    private final SpecialClothRepository specialClothRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SpecialCloth> findAllSpecialCloths() {
        return specialClothRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialCloth findSpecialClothById(Integer id) {
        SpecialCloth specialCloth = null;
        Optional<SpecialCloth> optional = specialClothRepository.findById(id);
        if (optional.isPresent()){
            specialCloth = optional.get();
        }
        return specialCloth;
    }

    @Override
    @Transactional
    public void saveSpecialCloth(SpecialCloth specialCloth) {
        specialClothRepository.save(specialCloth);
    }

    @Override
    @Transactional
    public void deleteSpecialClothById(Integer id) {
        specialClothRepository.deleteById(id);
    }
}