package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.ConstructionControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstructionControlRepository extends JpaRepository<ConstructionControl, Integer> {

}
