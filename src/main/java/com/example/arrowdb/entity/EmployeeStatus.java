package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "emp_status")
public class EmployeeStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_stat_id")
    private Integer epmStatId;

    @Column(name = "status_name")
    private String statusName;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "empStatus", fetch = FetchType.LAZY)
    private List<Employee> employeeList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "specClothStatus", fetch = FetchType.LAZY)
    private List<SpecialCloth> specialClothList = new ArrayList<>();

    public void addEmployeeToStatus(){
        employeeList.forEach(e -> e.setEmpStatus(this));
    }

    public void addSpecialClothToStatus(){
        specialClothList.forEach(e -> e.setSpecClothStatus(this));
    }

    @Override
    public String toString() {
        return statusName;
    }
}
