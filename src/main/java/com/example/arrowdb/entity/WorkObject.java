package com.example.arrowdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "work_object")
public class WorkObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_obj_id")
    private Integer workObjectId;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 1000, message = "Кол-во символов ль 2 до 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "order_number", unique=true)
    private String workObjectOrder;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов ль 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "lot_number", unique=true)
    private String workObjectLot;
    
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 3000, message = "Кол-во символов ль 2 до 3000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_name", unique=true)
    private String workObjectName;
    
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 500, message = "Кол-во символов ль 2 до 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "customer")
    private String workObjectCustomer;

    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_contract_cust_to_gencontr")
    private String workObjectContractCustomerTotGeneralContractor;

    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "general_contractor")
    private String workObjectGeneralContractor;
    
    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_contract_contr_to_gencontr")
    private String workObjectContractContractorTotGeneralContractor;

    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "value_of_contract")
    private String valueOfContract;

    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([,\\d\\s]+)?", message = "Допускаются только буквы Кириллицы и цифры")
    @Column(name = "price_of_contract")
    private String priceOfContract;

    @Min(0)
    @Max(100)
    @Column(name = "value_of_deposit")
    private Integer valueOfDeposit;

    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "chief_of_customer")
    private String chiefOfCustomer;

    private LocalDate dateOfStart;

    private LocalDate dateOfEnd;

    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_local_address")
    private String workObjectLocalAddress;

    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_storage_address")
    private String workObjectStorageAddress;

    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_comment")
    private String workObjectComment;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "workObject", fetch = FetchType.LAZY)
    private List<WorkInstrument> workInstrumentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "workObject", fetch = FetchType.LAZY)
    private List<MeasInstrument> measInstrumentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "workObject", fetch = FetchType.LAZY)
    private List<ConstructionControl> constructionControlList = new ArrayList<>();

    private Integer constructionControlActive;

    private Integer constructionControlClosed;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "work_object_status")
    private WorkObjectStatus workObjectStat;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "work_object_chief")
    private Employee workObjectChief;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "skeep_work_obj_join",
            joinColumns = @JoinColumn(name = "join_w_obj_id"),
            inverseJoinColumns = @JoinColumn(name = "join_skeep_id"))
    private  List<Employee> storeKeeperList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "pto_work_obj_join",
            joinColumns = @JoinColumn(name = "join_w_obj_id"),
            inverseJoinColumns = @JoinColumn(name = "join_pto_id"))
    private List<Employee> PTOList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "suppl_work_obj_join",
            joinColumns = @JoinColumn(name = "join_w_obj_id"),
            inverseJoinColumns = @JoinColumn(name = "join_suppl_id"))
    private List<Employee> supplierList = new ArrayList<>();

    public void addWorkInstrumentListToWorkObject() {
        workInstrumentList.forEach(e-> e.setWorkObject(this));
    }

    public void addMeasInstrumentListToWorkObject() {
        measInstrumentList.forEach(e-> e.setWorkObject(this));
    }

    public void addConstructionControlListToWorkObject() {
        constructionControlList.forEach(e-> e.setWorkObject(this));
    }

    public void addEmployeeToSupplierList(){
        supplierList.forEach(e -> e.getWorkObjectSupplierList().add(this));
    }

    public void addEmployeeToPTOList(){
        PTOList.forEach(e -> e.getWorkObjectPTOList().add(this));
    }

    public void addEmployeeToStoreKeeperList(){
        PTOList.forEach(e -> e.getWorkObjectSupplierList().add(this));
    }

    public void setWorkObjectName(String workObjectName) {
        try {
            this.workObjectName = workObjectName;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setWorkObjectCustomer(String workObjectCustomer) {
        try {
            this.workObjectCustomer = workObjectCustomer;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void setWorkObjectOrder(String workObjectOrder) {
        try {
            this.workObjectOrder = workObjectOrder;
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public Integer getConstructionControlActive() {
        return constructionControlActive = constructionControlList.stream()
                .filter(e -> e.getWarningStatus().getStatusName().equals("Действующий"))
                .toList()
                .size();
    }

    public Integer getConstructionControlClosed() {
        return constructionControlClosed = constructionControlList.stream()
                .filter(e -> e.getWarningStatus().getStatusName().equals("Закрыт"))
                .toList()
                .size();
    }

    @Override
    public String toString() {
        return String.format("%s %S", workObjectLot, workObjectOrder);
    }
}
