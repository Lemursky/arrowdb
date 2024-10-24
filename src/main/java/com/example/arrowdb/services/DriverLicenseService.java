package com.example.arrowdb.services;

import com.example.arrowdb.entity.DriverLicense;

import java.util.List;

public interface DriverLicenseService {
    List<DriverLicense> findAllDriverLicense();
    DriverLicense findDriverLicenseById(Integer id);
}
