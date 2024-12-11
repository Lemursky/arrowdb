package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(nativeQuery = true, value = "select * from employees where employee_status!=:status " +
            "and (login is null or length(login) = 0)")
    List<Employee> findEmployeeByParameters(int status);

    @Query(nativeQuery = true, value = "select email from employees where login=:login")
    String findEmailByLogin(String login);

}