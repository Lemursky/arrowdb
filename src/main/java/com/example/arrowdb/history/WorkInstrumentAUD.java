package com.example.arrowdb.history;

import com.example.arrowdb.entity.ConditionForTechn;
import com.example.arrowdb.entity.ConditionForWork;
import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.WorkObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "w_instruments_aud", schema = "history")
public class WorkInstrumentAUD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer Id;

    @Column(name = "onec_number")
    private String workOneCNumber;

    @Column(name = "invent_number")
    private String workInventNumber;

    @Column(name = "serial_number")
    private String workSerialNumber;

    @Column(name = "instr_name")
    private String workInstrName;

    @Column(name = "instr_model")
    private String workInstrModel;

    @Column(name = "instr_manufacturer")
    private String workInstrManufacturer;

    @Column(name = "date_of_purchase")
    private LocalDate workDateOfPurchase;

    @Column(name = "date_of_events")
    private LocalDate workDateOfEvents;

    @Column(name = "time_exp")
    private String workTimeExperience;

    @Column(name = "guarantee_period")
    private Integer workGuaranteePeriod;

    @Column(name = "time_of_guarantee")
    private String workTimeOfGuarantee;

    @Column(name = "service_period")
    private Integer workServicePeriod;

    @Column(name = "time_of_service")
    private String workTimeOfService;

    @Column(name = "broken_date")
    private LocalDate brokenDate;

    @Column(name = "start_rep_date")
    private LocalDate startRepDate;

    @Column(name = "end_rep_date")
    private LocalDate endRepDate;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "w_status")
    private ConditionForWork conditionForWork;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "t_status")
    private ConditionForTechn conditionForTechn;

    @Column(name = "instr_comment")
    private String workInstrComment;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "work_object")
    private WorkObject workObject;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "rev")
    private ExampleRevEntity exampleRevEntity;

}