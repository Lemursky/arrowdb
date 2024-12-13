package com.example.arrowdb.entity;

import com.example.arrowdb.enums.ClothSizeENUM;
import com.example.arrowdb.enums.DriverLicenseENUM;
import com.example.arrowdb.enums.EmployeeStatusENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Getter @Setter @NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employees")
@AuditTable(value = "employee_aud", schema = "history")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    @OrderBy
    private Integer empId;

    @Column(name = "login")
    private String login;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 45, message = "Кол-во символов от 2 до 45")
    @Pattern(regexp = "^[а-яА-ЯёЁ]+$", message = "только - алфавит: Кириллица")
    @Column(name = "sur_name")
    private String surName;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 45, message = "Кол-во символов от 2 до 45")
    @Pattern(regexp = "^[а-яА-ЯёЁ]+$", message = "только - алфавит: Кириллица")
    @Column(name = "name")
    private String name;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 45, message = "Кол-во символов от 2 до 45")
    @Pattern(regexp = "^[а-яА-ЯёЁ]+$", message = "только - алфавит: Кириллица")
    @Column(name = "middle_name")
    private String middleName;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "profession")
    private Profession profession;

    @Audited
    @Pattern(regexp = "[+7]{0,}[(]{0,}[0-9]{0,}[)]{0,}[0-9]{0,}[-]{0,}[0-9]{0,}[-]{0,}[0-9]{0,}",
            message = "Номер телефона должен состоять из 16 знаков в формате '+7(000)000-00-00'")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Audited
    @Email(regexp = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+){0,1}",
            message = "только - формат ввода: name@example.com")
    @Column(name = "email")
    private String email;

    @Audited
    @PastOrPresent
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @PastOrPresent
    @Column(name = "close_date")
    private LocalDate closeDate;

    private String timeExperience;

    @Audited
    @Min(100)
    @Max(300)
    @Column(name = "height")
    private Integer height;

    @Audited
    @Column(name = "cloth_size")
    private ClothSizeENUM clothSizeENUM;

    @Audited
    @Min(15)
    @Max(60)
    @Column(name = "shoes_size")
    private Integer shoesSize;

    @Audited
    @Column(name = "employee_status")
    private EmployeeStatusENUM employeeStatusENUM;

    @Audited
    @Column(name = "driver_license")
    private List<DriverLicenseENUM> driverLicenseENUM;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<PersonalInstrument> personalInstrumentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<WorkInstrument> workInstrumentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<MeasInstrument> measInstrumentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "employee", fetch = FetchType.LAZY)
    private List<SpecialClothEmployee> specialClothEmployeeList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "workObjectChief", fetch = FetchType.LAZY)
    private List<WorkObject> workObjectChiefList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "responsibleFromContractor", fetch = FetchType.LAZY)
    private List<ConstructionControl> responsibleFromContractorList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "responsibleFromSKContractor", fetch = FetchType.LAZY)
    private List<ConstructionControl> responsibleFromSKContractorList = new ArrayList<>();

    @ManyToMany(mappedBy = "storeKeeperList", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<WorkObject> workObjectStoreKeeperList = new ArrayList<>();

    @ManyToMany(mappedBy = "supplierList", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<WorkObject> workObjectSupplierList = new ArrayList<>();

    @ManyToMany(mappedBy = "PTOList", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<WorkObject> workObjectPTOList = new ArrayList<>();

    public void setSurName(String surName) {
        try {
            this.surName = surName.substring(0, 1).toUpperCase() + surName.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setName(String name) {
        try {
            this.name = name.substring(0, 1).toUpperCase() + name.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setMiddleName(String middleName) {
        try {
            this.middleName = middleName.substring(0, 1).toUpperCase() + middleName.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.equals("+7(___)___-__-__") || phoneNumber.isEmpty()) {
            this.phoneNumber = "";
        } else {
            this.phoneNumber = phoneNumber;
        }
    }

    public void setEmail(String email) {
        if (email.isEmpty()) {
            this.email = "";
        } else {
            this.email = email.trim();
        }
    }

    public String getTimeExperience() {
        if (getHireDate() == null) {
            return "";
        } else {
            if (employeeStatusENUM.getTitle().equals("Закрыт") && closeDate != null) {
                LocalDate hireDate = getHireDate();
                Period period = hireDate.until(closeDate);
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
                return String.format(" (Стаж составил: %d %s %d мес %d дн)", year, old, month, day);
            } else {
                LocalDate hireDate = getHireDate();
                Period period = hireDate.until(LocalDate.now());
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
                return String.format(" (Стаж составляет: %d %s %d мес %d дн)", year, old, month, day);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s.%s.", surName, name.charAt(0), middleName.charAt(0));
    }

}