package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.UniteOfInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniteOfInstrumentRepository extends JpaRepository<UniteOfInstrument, Integer> {
}
