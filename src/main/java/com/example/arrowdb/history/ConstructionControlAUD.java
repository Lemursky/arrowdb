package com.example.arrowdb.history;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.WorkObject;
import com.example.arrowdb.enums.ConstructionControlStatusENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "constr_control_aud", schema = "history")
public class ConstructionControlAUD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "num_of_warning")
    private String numOfWarning;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Size(max = 1000)
    @Column(name = "warning_name")
    private String warningName;

    @Column(name = "dead_line")
    private LocalDate deadLine;

    @Column(name = "responsible_from_customer")
    private String responsibleFromCustomer;

//    @OneToMany
//    @JoinTable(name = "emp_duty_c_contr_join_aud",
//            joinColumns = @JoinColumn(name = "rev", referencedColumnName = "rev"),
//            inverseJoinColumns = @JoinColumn(name = "join_emp_duty_id",  referencedColumnName = "emp_id"))
//    private List<Employee> empDutyListAUD = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "from_contractor")
    private Employee responsibleFromContractor;

    @ManyToOne
    @JoinColumn(name = "from_sk_contractor")
    private Employee responsibleFromSKContractor;

    @Column(name = "responsible_from_contractor")
    private String responsibleFromSubContractor;

    @Column(name = "date_of_extension")
    private LocalDate dateOfExtension;

    @Column(name = "constr_control_status")
    private ConstructionControlStatusENUM constructionControlStatusENUM;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "work_object")
    private WorkObject workObject;

    @OneToOne
    @JoinColumn(name = "rev")
    private ExampleRevEntity exampleRevEntity;
}
