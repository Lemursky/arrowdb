package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualityRepository extends JpaRepository<Quality, Integer> {
}
