package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "driver_license")
@AuditTable(value = "driver_license_aud", schema = "history")
public class DriverLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dl_id")
    private Integer drLiId;

    @Column(name = "dr_li_name")
    private String drLiName;

    @Column(name = "annotation")
    private String annotation;

    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToMany(mappedBy = "driverLicenseEmpList", cascade = CascadeType.REFRESH)
    private List<Employee> employeeList = new ArrayList<>();

    @Override
    public String toString() {
        return drLiName;
    }
}