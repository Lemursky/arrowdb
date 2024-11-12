package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "conditions_for_pers")
public class ConditionForPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_cond_id")
    private Integer pCondId;

    @Column(name = "p_condition")
    private String pConditionName;

    @Column(name = "p_annotation")
    private String pAnnotation;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "conditionForPersonal", fetch = FetchType.LAZY)
    private List<PersonalInstrument> personalInstrumentList = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s %s", pConditionName, pAnnotation);
    }
}