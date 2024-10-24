package com.example.arrowdb.history;

import com.example.arrowdb.entity.DriverLicense;
import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.EmployeeStatus;
import com.example.arrowdb.entity.Profession;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Column(name = "revtype")
    private Integer revisionType;

    @Column(name = "cloth_size")
    private String clothSize;

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

    @ManyToOne
    @JoinColumn(name = "employee_status")
    private EmployeeStatus empStatus;

    @OneToOne
    @JoinColumn(name = "rev")
    private ExampleRevEntity exampleRevEntity;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "employees_driver_license_join_aud",
            joinColumns = @JoinColumn(name = "rev", referencedColumnName = "rev"),
            inverseJoinColumns = @JoinColumn(name = "join_dl_id", referencedColumnName = "dl_id"))
    private List<DriverLicense> driverLicenseAUDList = new ArrayList<>();

}