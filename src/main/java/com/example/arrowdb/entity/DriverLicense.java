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

@Getter @Setter @NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "driver_license")
public class DriverLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dl_id")
    private Integer drLiId;

    @Column(name = "dr_li_name")
    private String drLiName;

    @Column(name = "annotation")
    private String annotation;

    @ManyToMany(mappedBy = "driverLicenseEmpList", cascade = CascadeType.REFRESH)
    private List<Employee> employeeList = new ArrayList<>();

    public void setDrLiName(String drLiName) {
        try {
            this.drLiName = drLiName.trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setAnnotation(String annotation) {
        try {
            this.annotation = annotation.substring(0, 1).toUpperCase() + annotation.substring(1);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public String toString() {
        return drLiName;
    }
}