package com.example.arrowdb.entity;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "w_instruments")
@AuditTable(value = "w_instruments_aud", schema = "history")
public class WorkInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instr_id")
    private Integer workInstrId;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([-\\dа-яА-Я-a\\s]+)?",
            message = "только - алфавит: Кириллица; цифры; символы: -")
    @Column(name = "onec_number")
    private String workOneCNumber;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "invent_number", unique=true)
    private String workInventNumber;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "serial_number")
    private String workSerialNumber;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 200, message = "Кол-во символов от 2 до 200")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr_name")
    private String workInstrName;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 200, message = "Кол-во символов от 2 до 200")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr_model")
    private String workInstrModel;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 200, message = "Кол-во символов от 2 до 200")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr_manufacturer")
    private String workInstrManufacturer;

    @Audited
    @PastOrPresent
    @Column(name = "date_of_purchase")
    private LocalDate workDateOfPurchase;

    @Audited
    @PastOrPresent
    @Column(name = "date_of_events")
    private LocalDate workDateOfEvents;

    @Audited
    @Column(name = "time_exp")
    private String workTimeExperience;

    @Audited
    @Min(1)
    @Max(100)
    @Column(name = "guarantee_period")
    private Integer workGuaranteePeriod;

    @Audited
    @Column(name = "time_of_guarantee")
    private String workTimeOfGuarantee;

    @Audited
    @Min(1)
    @Max(50)
    @Column(name = "service_period")
    private Integer workServicePeriod;

    @Audited
    @Column(name = "time_of_service")
    private String workTimeOfService;

    @Column(name = "delete_act")
    private String deleteAct;

    @PastOrPresent
    @Column(name = "close_date")
    private LocalDate closeDate;

    @Audited
    @PastOrPresent
    @Column(name = "broken_date")
    private LocalDate brokenDate;

    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "broken_annotation")
    private String brokenAnnotation;

    @Audited
    @PastOrPresent
    @Column(name = "start_rep_date")
    private LocalDate startRepairDate;

    @Audited
    @FutureOrPresent
    @Column(name = "end_rep_date")
    private LocalDate endRepairDate;

    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "repair_annotation")
    private String repairAnnotation;

    @Audited
    @Column(name = "issue_date")
    private LocalDate issueDate;

    private String issueDateVariants;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "w_status")
    private ConditionForWork conditionForWork;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "t_status")
    private ConditionForTechn conditionForTechn;

    @Audited
    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr_comment")
    private String workInstrComment;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "work_object")
    private WorkObject workObject;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee")
    private Employee employee;

    public void setWorkInventNumber(String workInventNumber) {
        try {
            this.workInventNumber = workInventNumber;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setWorkSerialNumber(String workSerialNumber) {
        try {
            this.workSerialNumber = workSerialNumber;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setWorkOneCNumber(String workOneCNumber) {
        try {
            this.workOneCNumber = workOneCNumber.substring(0, 2).toUpperCase() +
                    workOneCNumber.substring(2).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setWorkInstrName(String workInstrName) {
        try {
            this.workInstrName = workInstrName.substring(0, 1).toUpperCase() +
                    workInstrName.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setWorkInstrModel(String workInstrModel) {
        try {
            this.workInstrModel = workInstrModel.substring(0, 1).toUpperCase() +
                    workInstrModel.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setWorkInstrManufacturer(String workInstrManufacturer) {
        try {
            this.workInstrManufacturer = workInstrManufacturer.substring(0, 1).toUpperCase() +
                    workInstrManufacturer.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setBrokenAnnotation(String brokenAnnotation) {
        try {
            this.brokenAnnotation = brokenAnnotation;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setRepairAnnotation(String repairAnnotation) {
        try {
            this.repairAnnotation = repairAnnotation;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public String getWorkTimeExperience() {
        if (getWorkDateOfPurchase() == null) {
            return "";
        } else {
            LocalDate dateOfPurchase = getWorkDateOfPurchase();
            Period period = dateOfPurchase.until(LocalDate.now());
            int year = period.getYears();
            int month = period.getMonths();
            int day = period.getDays();
            String old = "";
            if (year == 0) old = "лет";
            else if (year == 1) old = "год";
            else if (year >= 2 && year <= 4) old = "года";
            else if (year >= 5 && year < 20) old = "лет";
            else if (year == 21) old = "год";
            else if (year >= 22 && year <= 24) old = "года";
            else if (year >= 25 && year < 30) old = "лет";
            return String.format(" (В строю: %d %s %d мес %d дн)", year, old, month, day);
        }
    }

    public String getWorkTimeOfGuarantee() {
        if (getWorkDateOfPurchase() == null || getWorkGuaranteePeriod() == null) {
            return "";
        } else {
            LocalDate localDate = getWorkDateOfPurchase();
            localDate = localDate.plusMonths(getWorkGuaranteePeriod());
            return String.format(" (Гарантия действует до: %s)",
                    localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    public String getWorkTimeOfService() {
        if (getWorkDateOfPurchase() == null || getWorkServicePeriod() == null) {
            return "";
        } else {
            LocalDate localDate = getWorkDateOfPurchase();
            localDate = localDate.plusYears(getWorkServicePeriod());
            return String.format(" (Срок службы до: %s)",
                    localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    public String getIssueDateVariants() {
        if(issueDate.isAfter(LocalDate.now())){
            return String.format("План. дата выдачи: %s (в резерве)",
                    issueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        return String.format("Дата выдачи: %s",
                issueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    @Override
    public String toString() {
        return String.format("%s %s %s сер.№ %s инв.№ %s",
                getWorkInstrName(), getWorkInstrManufacturer(), getWorkInstrModel(), getWorkSerialNumber(), getWorkInventNumber());
    }

}