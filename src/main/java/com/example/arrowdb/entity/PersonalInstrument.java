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
@Table(name = "p_instruments")
@AuditTable(value = "p_instruments_aud", schema = "history")
public class PersonalInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instr_id")
    private Integer personalInstrId;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([-\\dа-яА-Я-a\\s]+)?",
            message = "только - алфавит: Кириллица; цифры; символы: -")
    @Column(name = "onec_number")
    private String personalOneCNumber;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "invent_number", unique=true)
    private String personalInventNumber;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "serial_number")
    private String personalSerialNumber;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 200, message = "Кол-во символов от 2 до 200")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr-name")
    private String personalInstrName;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 200, message = "Кол-во символов от 2 до 200")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr-model")
    private String personalInstrModel;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 200, message = "Кол-во символов от 2 до 200")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr_manufacturer")
    private String personalInstrManufacturer;

    @Audited
    @PastOrPresent
    @Column(name = "date_of_purchase")
    private LocalDate personalDateOfPurchase;

    @Audited
    @PastOrPresent
    @Column(name = "date_of_events")
    private LocalDate personalDateOfEvents;

    @Audited
    @Column(name = "time_exp")
    private String personalTimeExperience;

    @Audited
    @Min(1)
    @Max(100)
    @Column(name = "guarantee_period")
    private Integer personalGuaranteePeriod;

    @Audited
    @Column(name = "time_of_guarantee")
    private String personalTimeOfGuarantee;

    @Audited
    @Min(1)
    @Max(50)
    @Column(name = "service_period")
    private Integer personalServicePeriod;

    @Audited
    @Column(name = "time_of_service")
    private String personalTimeOfService;

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
    @JoinColumn(name = "p_status")
    private ConditionForPersonal conditionForPersonal;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "t_status")
    private ConditionForTechn conditionForTechn;

    @Audited
    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr_comment")
    private String personalInstrComment;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee")
    private Employee employee;

    public void setPersonalInventNumber(String personalInventNumber) {
        try {
            this.personalInventNumber = personalInventNumber;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setPersonalSerialNumber(String personalSerialNumber) {
        try {
            this.personalSerialNumber = personalSerialNumber;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setPersonalOneCNumber(String personalOneCNumber) {
        try {
            this.personalOneCNumber = personalOneCNumber.substring(0, 2).toUpperCase() +
                    personalOneCNumber.substring(2).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setPersonalInstrName(String personalInstrName) {
        try {
            this.personalInstrName = personalInstrName.substring(0, 1).toUpperCase() +
                    personalInstrName.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setPersonalInstrModel(String personalInstrModel) {
        try {
            this.personalInstrModel = personalInstrModel.substring(0, 1).toUpperCase() +
                    personalInstrModel.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setPersonalInstrManufacturer(String personalInstrManufacturer) {
        try {
            this.personalInstrManufacturer = personalInstrManufacturer.substring(0, 1).toUpperCase() +
                    personalInstrManufacturer.substring(1).trim();
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

    public String getPersonalTimeExperience() {
        if (getPersonalDateOfPurchase() == null) {
            return "";
        } else {
            if (getConditionForTechn().getTConditionName().equals("Списан") && closeDate != null) {
                LocalDate dateOfPurchase = getPersonalDateOfPurchase();
                Period period = dateOfPurchase.until(closeDate);
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
                return String.format(" (Состоял в строю: %d %s %d мес %d дн)", year, old, month, day);
            } else {
                LocalDate dateOfPurchase = getPersonalDateOfPurchase();
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
    }

    public String getPersonalTimeOfGuarantee() {
        if (getPersonalDateOfPurchase() == null || getPersonalGuaranteePeriod() == null) {
            return "";
        } else {
            LocalDate localDate = getPersonalDateOfPurchase();
            localDate = localDate.plusMonths(getPersonalGuaranteePeriod());
            return String.format(" (Гарантия действует до: %s)",
                    localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    public String getPersonalTimeOfService() {
        if (getPersonalDateOfPurchase() == null || getPersonalServicePeriod() == null) {
            return "";
        } else {
            LocalDate localDate = getPersonalDateOfPurchase();
            localDate = localDate.plusYears(getPersonalServicePeriod());
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
                getPersonalInstrName(), getPersonalInstrManufacturer(), getPersonalInstrModel(), getPersonalSerialNumber(), getPersonalInventNumber());
    }

}