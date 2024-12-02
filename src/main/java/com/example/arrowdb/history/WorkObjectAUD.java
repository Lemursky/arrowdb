package com.example.arrowdb.history;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.enums.WorkObjectStatusENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "work_object_aud", schema = "history")
public class WorkObjectAUD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Size(max = 1000)
    @Column(name = "order_number")
    private String workObjectOrder;

    @Column(name = "lot_number")
    private String workObjectLot;

    @Size(max = 3000)
    @Column(name = "work_object_name")
    private String workObjectName;

    @Size(max = 500)
    @Column(name = "customer")
    private String workObjectCustomer;

    @Size(max = 500)
    @Column(name = "work_object_contract_cust_to_gencontr")
    private String workObjectContractCustomerTotGeneralContractor;

    @Size(max = 500)
    @Column(name = "general_contractor")
    private String workObjectGeneralContractor;

    @Size(max = 500)
    @Column(name = "work_object_contract_contr_to_gencontr")
    private String workObjectContractContractorTotGeneralContractor;

    @Size(max = 500)
    @Column(name = "value_of_contract")
    private String valueOfContract;

    @Size(max = 500)
    @Column(name = "price_of_contract")
    private String priceOfContract;

    @Column(name = "value_of_deposit")
    private Integer valueOfDeposit;

    @Size(max = 1000)
    @Column(name = "chief_of_customer")
    private String chiefOfCustomer;

    @Column(name = "date_of_start")
    private LocalDate dateOfStart;

    @Column(name = "date_of_end")
    private LocalDate dateOfEnd;

    @Size(max = 1000)
    @Column(name = "work_object_local_address")
    private String workObjectLocalAddress;

    @Size(max = 1000)
    @Column(name = "work_object_storage_address")
    private String workObjectStorageAddress;

    @Size(max = 1000)
    @Column(name = "work_object_comment")
    private String workObjectComment;

    @Column(name = "work_object_status")
    private WorkObjectStatusENUM workObjectStatusENUM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_object_chief")
    private Employee workObjectChief;

    @OneToOne
    @JoinColumn(name = "rev")
    private ExampleRevEntity exampleRevEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "skeep_work_obj_join_aud",
            joinColumns = @JoinColumn(name = "rev", referencedColumnName = "rev"),
            inverseJoinColumns = @JoinColumn(name = "join_skeep_id", referencedColumnName = "emp_id"))
    private  List<Employee> storeKeeperAUDList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "pto_work_obj_join_aud",
            joinColumns = @JoinColumn(name = "rev", referencedColumnName = "rev"),
            inverseJoinColumns = @JoinColumn(name = "join_pto_id", referencedColumnName = "emp_id"))
    private List<Employee> PTOAUDList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "suppl_work_obj_join_aud",
            joinColumns = @JoinColumn(name = "rev", referencedColumnName = "rev"),
            inverseJoinColumns = @JoinColumn(name = "join_suppl_id", referencedColumnName = "emp_id"))
    private List<Employee> supplierAUDList = new ArrayList<>();

}
