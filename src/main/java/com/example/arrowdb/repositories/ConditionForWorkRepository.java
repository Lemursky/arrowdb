package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.ConditionForWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionForWorkRepository extends JpaRepository<ConditionForWork, Integer> {
    ConditionForWork findConditionForWorkBywConditionName(String name);
}
