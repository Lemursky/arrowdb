package com.example.arrowdb.repositories;

import com.example.arrowdb.history.WorkInstrumentAUD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WorkInstrumentAUDRepository extends JpaRepository<WorkInstrumentAUD, Integer> {

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "select * from history.w_instruments_aud where instr_id=:id order by rev desc")
    List<WorkInstrumentAUD> findAllWorkInstrumentAUDById(Integer id);
}
