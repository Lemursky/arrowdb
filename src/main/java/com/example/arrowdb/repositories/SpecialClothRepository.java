package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.SpecialCloth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SpecialClothRepository extends JpaRepository<SpecialCloth, Integer> {

}
