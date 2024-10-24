package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.SpecialCloth;
import com.example.arrowdb.entity.SpecialClothEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialClothEmployeeRepository extends JpaRepository<SpecialClothEmployee, Integer> {

    @Query(nativeQuery = true, value = "select * from spec_cloth_emp where emp_id=:id")
    List<SpecialClothEmployee> findAllSpecialClothEmployeeByEmployeeId(Integer id);

    @Query(nativeQuery = true, value = "select emp_id from spec_cloth_emp where spec_cloth_name=:id")
    List<Integer> findAllEmployeeBySpecialClothEmployeeId(Integer id);

    @Query(nativeQuery = true, value = "select emp_id from spec_cloth_emp where spec_cloth_emp_id=:id")
    Integer findEmployeeIdBySpecialClothEmployeeId(Integer id);

    @Query(nativeQuery = true, value = "select spec_cloth_name from spec_cloth_emp where emp_id=:id")
    List<Integer> findSpecialClothByEmployeeId (Integer id);

}