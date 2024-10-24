package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.ConditionForPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionForPersonalRepository extends JpaRepository<ConditionForPersonal, Integer> {
    ConditionForPersonal findConditionForPersonalBypConditionName(String name);
}
