package com.example.arrowdb.repositories;

import com.example.arrowdb.history.ConstructionControlAUD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConstructionControlAUDRepository extends JpaRepository<ConstructionControlAUD, Integer>{

    @Transactional
    @Query(nativeQuery = true, value = "select * from history.constr_control_aud where " +
            "constr_control_id=:id order by rev desc")
    List<ConstructionControlAUD> findAllConstructionControlsAUDById(Integer id);

}