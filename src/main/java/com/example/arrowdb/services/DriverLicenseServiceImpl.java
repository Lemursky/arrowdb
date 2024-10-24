package com.example.arrowdb.services;

import com.example.arrowdb.entity.DriverLicense;
import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.repositories.DriverLicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverLicenseServiceImpl implements DriverLicenseService {

    private final DriverLicenseRepository driverLicenseRepository;

    @Override
    @Transactional
    public List<DriverLicense> findAllDriverLicense() {
        return driverLicenseRepository.findAll();
    }

    @Override
    @Transactional
    public DriverLicense findDriverLicenseById(Integer id) {
        DriverLicense driverLicense = null;
        Optional<DriverLicense> optional = driverLicenseRepository.findById(id);
        if (optional.isPresent()) {
            driverLicense = optional.get();
        }
        return driverLicense;
    }
}
