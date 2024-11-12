package com.example.arrowdb.entity;

import com.example.arrowdb.enums.TechnicalConditionENUM;
import com.example.arrowdb.enums.WorkConditionENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "m_instruments")
public class MeasInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instr_id")
    private Integer measInstrId;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([-\\dа-яА-Я-a\\s]+)?",
            message = "только - алфавит: Кириллица; цифры; символы: -")
    @Column(name = "onec_number")
    private String measOneCNumber;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "invent_number", unique=true)
    private String measInventNumber;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "serial_number")
    private String measSerialNumber;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr-name")
    private String measInstrName;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr-model")
    private String measInstrModel;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "instr_manufacturer")
    private String measInstrManufacturer;

    @PastOrPresent
    @Column(name = "date_of_purchase")
    private LocalDate measDateOfPurchase;

    @PastOrPresent
    @Column(name = "date_of_events")
    private LocalDate measDateOfEvents;

    @Column(name = "time_exp")
    private String measTimeExperience;

    @Min(1)
    @Max(100)
    @Column(name = "guarantee_period")
    private Integer measGuaranteePeriod;

    @Column(name = "time_of_guarantee")
    private String measTimeOfGuarantee;

    @Min(1)
    @Max(50)
    @Column(name = "service_period")
    private Integer measServicePeriod;

    @Column(name = "time_of_service")
    private String measTimeOfService;

    @Column(name = "delete_act")
    private String deleteAct;

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(name = "broken_date")
    private LocalDate brokenDate;

    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "broken_annotation")
    private String brokenAnnotation;

    @PastOrPresent
    @Column(name = "start_rep_date")
    private LocalDate startRepairDate;

    @FutureOrPresent
    @Column(name = "end_rep_date")
    private LocalDate endRepairDate;

    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "repair_annotation")
    private String repairAnnotation;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    private String issueDateVariants;

    @Pattern(regexp = "([,\\d\\s]+)?")
    @Column(name = "meas_price")
    private String measInstrumentPrice;

    @PastOrPresent
    @Column(name = "date_first")
    private LocalDate measDateFirst;

    @FutureOrPresent
    @Column(name = "date_second")
    private LocalDate measDateSecond;

    @Column(name = "link_view")
    private String linkView;

    private String certificateStat;

    @Column(name = "t_status")
    private TechnicalConditionENUM technicalConditionENUM;

    @Column(name = "w_status")
    private WorkConditionENUM workConditionENUM;

    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
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

    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "verification_history")
    private String verificationHistory;

    public void setMeasInventNumber(String measInventNumber) {
        try {
            this.measInventNumber = measInventNumber;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setMeasSerialNumber(String measSerialNumber) {
        try {
            this.measSerialNumber = measSerialNumber;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setMeasOneCNumber(String measOneCNumber) {
        try {
            this.measOneCNumber = measOneCNumber.substring(0, 2).toUpperCase() +
                    measOneCNumber.substring(2).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setMeasInstrName(String measInstrName) {
        try {
            this.measInstrName = measInstrName.substring(0, 1).toUpperCase() +
                    measInstrName.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setMeasInstrModel(String measInstrModel) {
        try {
            this.measInstrModel = measInstrModel.substring(0, 1).toUpperCase() +
                    measInstrModel.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setMeasInstrManufacturer(String measInstrManufacturer) {
        try {
            this.measInstrManufacturer = measInstrManufacturer.substring(0, 1).toUpperCase() + measInstrManufacturer.substring(1).trim();
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

    public void setLinkView(String linkView) {
        try {
            this.linkView = linkView.trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public String getMeasTimeExperience() {
        if (getMeasDateOfPurchase() == null) {
            return "";
        } else {
            LocalDate dateOfPurchase = getMeasDateOfPurchase();
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

    public String getMeasTimeOfGuarantee() {
        if (getMeasDateOfPurchase() == null || getMeasGuaranteePeriod() == null) {
            return "";
        } else {
            LocalDate localDate = getMeasDateOfPurchase();
            localDate = localDate.plusMonths(getMeasGuaranteePeriod());
            return String.format(" (Гарантия действует до: %s)",
                    localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    public String getMeasTimeOfService() {
        if (getMeasDateOfPurchase() == null || getMeasServicePeriod() == null) {
            return "";
        } else {
            LocalDate localDate = getMeasDateOfPurchase();
            localDate = localDate.plusYears(getMeasServicePeriod());
            return String.format(" (Срок службы до: %s)",
                    localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    public String getCertificateStat() {
        if (getMeasDateFirst() != null && getMeasDateSecond() != null) {
            if (LocalDate.now().isBefore(getMeasDateSecond())) {
                return certificateStat = String.format("Действует (до %s)",
                        getMeasDateSecond().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            } else {
                return certificateStat = String.format("Закончилась (%s)",
                        getMeasDateSecond().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        } else {
            return certificateStat = "";
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
        return String.format("%s %s сер.№ %s инв.№ %s",
                getMeasInstrName(), getMeasInstrModel(), getMeasSerialNumber(), getMeasInventNumber());
    }
}