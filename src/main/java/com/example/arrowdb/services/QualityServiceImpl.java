package com.example.arrowdb.services;

import com.example.arrowdb.entity.Quality;
import com.example.arrowdb.repositories.QualityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QualityServiceImpl implements QualityService{

    private final QualityRepository qualityRepository;

    @Override
    @Transactional
    public List<Quality> getAllQualities() {
        return qualityRepository.findAll();
    }

    @Override
    @Transactional
    public Quality getQualityById(Integer id) {
        Quality quality = null;
        Optional<Quality> optional = qualityRepository.findById(id);
        if(optional.isPresent()){
            quality = optional.get();
        }
        return quality;
    }
}
