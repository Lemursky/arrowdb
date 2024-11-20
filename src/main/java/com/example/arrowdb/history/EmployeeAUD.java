package com.example.arrowdb.history;

import com.example.arrowdb.entity.Profession;
import com.example.arrowdb.enums.ClothSizeENUM;
import com.example.arrowdb.enums.DriverLicenseENUM;
import com.example.arrowdb.enums.EmployeeStatusENUM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee_aud", schema = "history")
public class EmployeeAUD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "rev_type")
    private Integer revisionType;

    @Column(name = "email")
    private String email;

    @Column(name = "height")
    private Integer height;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "shoes_size")
    private Integer shoesSize;

    @Column(name = "sur_name")
    private String surName;

    @ManyToOne
    @JoinColumn(name = "profession")
    private Profession profession;

    @Column(name = "employee_status")
    private EmployeeStatusENUM employeeStatusENUM;

    @Column(name = "driver_license")
    private List<DriverLicenseENUM> driverLicenseENUM;

    @Column(name = "cloth_size")
    private ClothSizeENUM clothSizeENUM;

    @OneToOne
    @JoinColumn(name = "rev")
    private ExampleRevEntity exampleRevEntity;

}