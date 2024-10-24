package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.DriverLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverLicenseRepository extends JpaRepository<DriverLicense, Integer> {
}
