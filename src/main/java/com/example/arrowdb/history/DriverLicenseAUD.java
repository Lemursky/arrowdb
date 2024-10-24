package com.example.arrowdb.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees_driver_license_join_aud", schema = "public")
public class DriverLicenseAUD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "rev")
    private Integer rev;

    @Column(name = "join_dl_id")
    private Integer dlId;

    @Column(name = "join_emp_id")
    private Integer empId;

    @Column(name = "revtype")
    private Integer revType;

}