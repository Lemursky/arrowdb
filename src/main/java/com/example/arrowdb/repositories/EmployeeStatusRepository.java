package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Integer> {
    EmployeeStatus findEmployeeStatusByStatusName(String name);
}
