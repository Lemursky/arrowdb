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

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "conditions_for_work")
public class ConditionForWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_cond_id")
    private Integer wCondId;

    @Column(name = "w_condition")
    private String wConditionName;

    @Column(name = "w_annotation")
    private String wAnnotation;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "conditionForWork", fetch = FetchType.LAZY)
    private List<WorkInstrument> workInstrumentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "conditionForWork", fetch = FetchType.LAZY)
    private List<MeasInstrument> measInstrumentList = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s %s", wConditionName, wAnnotation);
    }

}
