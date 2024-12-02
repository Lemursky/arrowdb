package com.example.arrowdb.repositories;

import com.example.arrowdb.history.PersonalInstrumentAUD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PersonalInstrumentAUDRepository extends JpaRepository<PersonalInstrumentAUD, Integer> {

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "select * from history.p_instruments_aud where instr_id=:id order by rev desc")
    List<PersonalInstrumentAUD> findAllPersonalInstrumentAUDById(Integer id);

}