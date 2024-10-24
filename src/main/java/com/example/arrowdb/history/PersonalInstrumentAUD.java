package com.example.arrowdb.history;

import com.example.arrowdb.entity.ConditionForPersonal;
import com.example.arrowdb.entity.ConditionForTechn;
import com.example.arrowdb.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_instruments_aud", schema = "history")
public class PersonalInstrumentAUD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "instr_id")
    private Integer personalInstrId;

    @Column(name = "onec_number")
    private String personalOneCNumber;

    @Column(name = "invent_number", unique=true)
    private String personalInventNumber;

    @Column(name = "serial_number")
    private String personalSerialNumber;

    @Column(name = "instr-name")
    private String personalInstrName;

    @Column(name = "instr-model")
    private String personalInstrModel;

    @Column(name = "instr_manufacturer")
    private String personalInstrManufacturer;

    @Column(name = "date_of_purchase")
    private LocalDate personalDateOfPurchase;

    @Column(name = "date_of_events")
    private LocalDate personalDateOfEvents;

    @Column(name = "time_exp")
    private String personalTimeExperience;

    @Column(name = "guarantee_period")
    private Integer personalGuaranteePeriod;

    @Column(name = "time_of_guarantee")
    private String personalTimeOfGuarantee;

    @Column(name = "service_period")
    private Integer personalServicePeriod;

    @Column(name = "time_of_service")
    private String personalTimeOfService;

    @ManyToOne
    @JoinColumn(name = "p_status")
    private ConditionForPersonal conditionForPersonal;

    @ManyToOne
    @JoinColumn(name = "t_status")
    private ConditionForTechn conditionForTechn;

    @Column(name = "instr_comment")
    private String personalInstrComment;

    @Column(name = "start_rep_date")
    private LocalDate startRepDate;

    @Column(name = "end_rep_date")
    private LocalDate endRepDate;

    @Column(name = "broken_date")
    private LocalDate brokenDate;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "employee")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "rev")
    private ExampleRevEntity exampleRevEntity;

}
