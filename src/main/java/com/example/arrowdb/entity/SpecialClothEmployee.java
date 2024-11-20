package com.example.arrowdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "spec_cloth_emp")
public class SpecialClothEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spec_cloth_emp_id")
    private Integer specClothEmpId;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_cloth_name")
    private SpecialCloth specialCloth;

    @NotNull
    @Column(name = "spec_cloth_date")
    private LocalDate specClothDateEmp;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee")
    private Employee employee;

    @JoinColumn(name = "emp_id")
    private Integer empId;

    @Override
    public String toString() {
        return String.format("%s | %s", specClothDateEmp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), specialCloth);
    }
}
