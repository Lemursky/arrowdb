package com.example.arrowdb.repositories;

import com.example.arrowdb.history.EmployeeAUD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeAUDRepository extends JpaRepository<EmployeeAUD, Integer> {

    @Query(nativeQuery = true, value = "select * from history.employee_aud where emp_id=:id order by rev desc")
    List<EmployeeAUD> findAllEmployeeAUDByEmployeeId(Integer id);

}
