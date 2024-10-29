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
@Table(name = "conditions_for_techn")
@AuditTable(value = "conditions_for_techn_aud", schema = "history")
public class ConditionForTechn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_cond_id")
    private Integer tCondId;

    @Column(name = "t_condition")
    private String tConditionName;

    @Column(name = "t_annotation")
    private String tAnnotation;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "conditionForTechn", fetch = FetchType.LAZY)
    private List<PersonalInstrument> personalInstrumentList = new ArrayList<>();

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "conditionForTechn", fetch = FetchType.LAZY)
    private List<WorkInstrument> workInstrumentList = new ArrayList<>();

//    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "conditionForTechn", fetch = FetchType.LAZY)
    private List<MeasInstrument> measInstrumentList = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s %s", tConditionName, tAnnotation);
    }

}
