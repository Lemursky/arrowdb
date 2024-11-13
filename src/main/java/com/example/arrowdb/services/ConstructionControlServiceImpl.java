package com.example.arrowdb.services;

import com.example.arrowdb.entity.ConstructionControl;
import com.example.arrowdb.repositories.ConstructionControlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConstructionControlServiceImpl implements ConstructionControlService{

    private final ConstructionControlRepository constructionControlRepository;

    @Override
    @Transactional
    public ConstructionControl findConstructionControlById(Integer id) {
        ConstructionControl constructionControl = null;
        Optional<ConstructionControl> optional = constructionControlRepository.findById(id);
        if (optional.isPresent()){
            constructionControl = optional.get();
        }
        return constructionControl;
    }

    @Override
    @Transactional
    public List<ConstructionControl> findAllConstructionControl() {
        return constructionControlRepository.findAll();
    }

    @Override
    @Transactional
    public void saveConstructionControl(ConstructionControl constructionControl) {
        constructionControlRepository.save(constructionControl);
    }

    @Override
    @Transactional
    public void deleteConstructionControlById(Integer id) {
        constructionControlRepository.deleteById(id);
    }

}
