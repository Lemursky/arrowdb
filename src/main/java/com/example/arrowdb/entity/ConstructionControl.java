package com.example.arrowdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "constr_control")
public class ConstructionControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "constr_control_id")
    private Integer constrControlId;
    
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 1, max = 100, message = "Кол-во символов максимум 100")
    @Pattern(regexp = "([|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "Допускаются только буквы Латинские, Кириллицы и цифры")
    @Column(name = "num_of_warning", unique = true)
    private String numOfWarning;
    
    @NotNull
    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;
    
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 1, max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "warning_name")
    private String warningName;
    
    @Column(name = "dead_line")
    private LocalDate deadLine;
    
    @Size(max = 100, message = "Кол-во символов максимум 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "responsible_from_customer")
    private String responsibleFromCustomer;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "emp_duty_c_contr_join",
            joinColumns = @JoinColumn(name = "join_c_contr_id"),
            inverseJoinColumns = @JoinColumn(name = "join_emp_duty_id"))
    private List<Employee> empDutyList = new ArrayList<>();

    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "responsible_from_contractor")
    private String responsibleFromContractor;

    @Column(name = "date_of_extension")
    private LocalDate dateOfExtension;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "constr_control_status")
    private WorkObjectStatus warningStatus;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "work_object")
    private WorkObject workObject;

    @Override
    public String toString() {
        return String.format("№ предупр.: %s; Объект: %s", numOfWarning, workObject);
    }
}