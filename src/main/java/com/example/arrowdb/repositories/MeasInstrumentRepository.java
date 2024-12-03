package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.MeasInstrument;
import com.example.arrowdb.entity.WorkInstrument;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeasInstrumentRepository extends JpaRepository<MeasInstrument, Integer> {

    @Query(nativeQuery = true, value = "select employee from m_instruments where employee=:id")
    List<Integer> findAllIdEmployees(Integer id);

    @Query(nativeQuery = true, value = "select work_object from m_instruments where work_object=:id")
    List<Integer> findAllMeasInstrumentByWorkObjectId(Integer id);

    @Query(nativeQuery = true, value = "select employee from m_instruments where instr_id=:id")
    Integer findEmployeeIdByMeasInstId(Integer id);

    @Query(nativeQuery = true, value = "select * from m_instruments where instr_id=:id")
    List<MeasInstrument> findAllMeasInstrumentById(Integer id);

//    @Query("select m from MeasInstrument m join fetch m.employee")
//    @NotNull
//    List<MeasInstrument> findAll();

}