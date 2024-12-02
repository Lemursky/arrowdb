package com.example.arrowdb.history;

import com.example.arrowdb.entity.Department;
import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.WorkObject;
import com.example.arrowdb.enums.TechnicalConditionENUM;
import com.example.arrowdb.enums.WorkConditionENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "m_instruments_aud", schema = "history")
public class MeasInstrumentAUD {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer Id;

    @Column(name = "onec_number")
    private String measOneCNumber;

    @Column(name = "invent_number")
    private String measInventNumber;

    @Column(name = "serial_number")
    private String measSerialNumber;

    @Column(name = "instr-name")
    private String measInstrName;

    @Column(name = "instr-model")
    private String measInstrModel;

    @Column(name = "instr_manufacturer")
    private String measInstrManufacturer;

    @Column(name = "date_of_purchase")
    private LocalDate measDateOfPurchase;

    @Column(name = "date_of_events")
    private LocalDate measDateOfEvents;

    @Column(name = "time_exp")
    private String measTimeExperience;

    @Column(name = "guarantee_period")
    private Integer measGuaranteePeriod;

    @Column(name = "time_of_guarantee")
    private String measTimeOfGuarantee;

    @Column(name = "service_period")
    private Integer measServicePeriod;

    @Column(name = "time_of_service")
    private String measTimeOfService;

    @Column(name = "broken_date")
    private LocalDate brokenDate;

    @Column(name = "start_rep_date")
    private LocalDate startRepairDate;

    @Column(name = "end_rep_date")
    private LocalDate endRepairDate;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "meas_price")
    private String measInstrumentPrice;

    @Column(name = "date_first")
    private LocalDate measDateFirst;

    @Column(name = "date_second")
    private LocalDate measDateSecond;

    @Column(name = "link_view")
    private String linkView;

    @Column(name = "t_status")
    private TechnicalConditionENUM technicalConditionENUM;

    @Column(name = "w_status")
    private WorkConditionENUM workConditionENUM;

    @Size(max = 1000)
    @Column(name = "instr_comment")
    private String measInstrComment;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "work_object")
    private WorkObject workObject;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "dep_status")
    private Department department;

    @Column(name = "verification_history")
    private String verificationHistory;

    @OneToOne
    @JoinColumn(name = "rev")
    private ExampleRevEntity exampleRevEntity;
}
