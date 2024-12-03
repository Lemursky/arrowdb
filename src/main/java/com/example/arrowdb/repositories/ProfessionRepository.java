package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Integer> {

//    @Query("select p from Profession p join fetch p.employeeList where p.profId=:id")
//    Optional<Profession> findById(int id);

}
