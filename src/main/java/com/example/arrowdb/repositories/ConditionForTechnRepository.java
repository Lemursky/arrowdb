package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.ConditionForPersonal;
import com.example.arrowdb.entity.ConditionForTechn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionForTechnRepository extends JpaRepository<ConditionForTechn, Integer> {
    ConditionForTechn findConditionForTechnBytConditionName(String name);
}
