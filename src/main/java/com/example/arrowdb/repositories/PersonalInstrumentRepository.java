package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.PersonalInstrument;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalInstrumentRepository extends JpaRepository<PersonalInstrument, Integer> {

    @Query(value = "select p from PersonalInstrument p left join fetch p.employee")
    @NotNull
    List<PersonalInstrument> findAll();

    @Query(nativeQuery = true, value = "select employee from p_instruments where employee=:id")
    List<Integer> findAllIdEmployees(Integer id);

    @Query(nativeQuery = true, value = "select employee from p_instruments where instr_id=:id")
    Integer findPersonalInstIdByEmployeeId(Integer id);

}