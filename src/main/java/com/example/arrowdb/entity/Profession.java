package com.example.arrowdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "professions")
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prof_id")
    private Integer profId;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов от 2 до 100")
    @Pattern(regexp = "[а-яА-Я-\\s]+", message = "только - алфавит: Кириллица")
    @Column(name = "profession_name", unique=true)
    private String professionName;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "quality")
    private Quality quality;

    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "speciality")
    private String speciality;

    @Min(1)
    @Max(30)
    @Column(name = "experience")
    private Integer experience;
    
    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "add_requirements")
    private String additionalRequirements;

    private String suffix;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profession", fetch = FetchType.LAZY)
    private List<Employee> employeeList = new ArrayList<>();

    public void setProfessionName(String professionName) {
        try {
            this.professionName = professionName.substring(0, 1).toUpperCase() + professionName.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setSpeciality(String speciality) {
        if (speciality.isEmpty()) {
            this.speciality = "";
        } else {
            this.speciality = speciality.trim();
        }
    }

    public void setAdditionalRequirements(String additionalRequirements) {
        if (additionalRequirements.isEmpty()) {
            this.additionalRequirements = "";
        } else {
            this.additionalRequirements = additionalRequirements.trim();
        }
    }

    public String getSuffix() {
        if (experience == null) {
            return "";
        } else {
            int year = experience;
            String old = "";
            if (year == 1) old = "год";
            else if (year >= 2 && year <= 4) old = "года";
            else if (year >= 5 && year < 20) old = "лет";
            else if (year == 21) old = "год";
            else if (year >= 22 && year <= 24) old = "года";
            else if (year >= 25 && year < 30) old = "лет";
            return String.format(" %s", old);
        }
    }

    @Override
    public String toString() {
        return professionName;
    }

}