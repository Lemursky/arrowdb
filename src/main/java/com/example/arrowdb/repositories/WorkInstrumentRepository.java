package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.PersonalInstrument;
import com.example.arrowdb.entity.WorkInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkInstrumentRepository extends JpaRepository<WorkInstrument, Integer> {

    @Query(nativeQuery = true, value = "select employee from w_instruments where employee=:id")
    List<Integer> findAllIdEmployees(Integer id);

    @Query(nativeQuery = true, value = "select work_object from w_instruments where work_object=:id")
    List<Integer> findAllWorkInstrumentByWorkObjectId(Integer id);

    @Query(nativeQuery = true, value = "select employee from w_instruments where instr_id=:id")
    Integer findEmployeeIdByWorkInstId(Integer id);

    @Query(nativeQuery = true, value = "select * from w_instruments where instr_id=:id")
    List<WorkInstrument> findAllWorkInstrumentById(Integer id);

}