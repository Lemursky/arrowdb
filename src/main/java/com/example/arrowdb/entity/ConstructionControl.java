package com.example.arrowdb.entity;

import com.example.arrowdb.enums.ConstructionControlStatusENUM;
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

@Getter @Setter @NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "constr_control")
@AuditTable(value = "constr_control_aud", schema = "history")
public class ConstructionControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "constr_control_id")
    private Integer constrControlId;

    @Column(name = "author")
    private String author;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 1, max = 100, message = "Кол-во символов максимум 100")
    @Pattern(regexp = "([|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "Допускаются только буквы Латинские, Кириллицы и цифры")
    @Column(name = "num_of_warning", unique = true)
    private String numOfWarning;

    @Audited
    @NotNull
    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 1, max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "warning_name")
    private String warningName;

    @Audited
    @Column(name = "dead_line")
    private LocalDate deadLine;

    @Audited
    @Size(max = 100, message = "Кол-во символов максимум 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "responsible_from_customer")
    private String responsibleFromCustomer;

    @Audited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_contractor")
    private Employee responsibleFromContractor;

    @Audited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_sk_contractor")
    private Employee responsibleFromSKContractor;

    @Audited
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "responsible_from_contractor")
    private String responsibleFromSubContractor;

    @Audited
    @Column(name = "date_of_extension")
    private LocalDate dateOfExtension;

    @Audited
    @Column(name = "constr_control_status")
    private ConstructionControlStatusENUM constructionControlStatusENUM;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "work_object")
    private WorkObject workObject;

    @Override
    public String toString() {
        return String.format("№ предупр.: %s; Объект: %s", numOfWarning, workObject);
    }
}