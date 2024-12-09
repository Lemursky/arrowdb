package com.example.arrowdb.entity;

import com.example.arrowdb.enums.WorkObjectStatusENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "work_object")
@AuditTable(value = "work_object_aud", schema = "history")
public class WorkObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_obj_id")
    private Integer workObjectId;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 1000, message = "Кол-во символов ль 2 до 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "order_number", unique=true)
    private String workObjectOrder;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 100, message = "Кол-во символов ль 2 до 100")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "lot_number", unique=true)
    private String workObjectLot;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 3000, message = "Кол-во символов ль 2 до 3000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_name", unique=true)
    private String workObjectName;

    @Audited
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 500, message = "Кол-во символов ль 2 до 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "customer")
    private String workObjectCustomer;

    @Audited
    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_contract_cust_to_gencontr")
    private String workObjectContractCustomerTotGeneralContractor;

    @Audited
    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "general_contractor")
    private String workObjectGeneralContractor;

    @Audited
    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_contract_contr_to_gencontr")
    private String workObjectContractContractorTotGeneralContractor;

    @Audited
    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "value_of_contract")
    private String valueOfContract;

    @Audited
    @Size(max = 500, message = "Кол-во символов максимум 500")
    @Pattern(regexp = "([,\\d\\s]+)?", message = "Допускаются только буквы Кириллицы и цифры")
    @Column(name = "price_of_contract")
    private String priceOfContract;

    @Audited
    @Min(0)
    @Max(100)
    @Column(name = "value_of_deposit")
    private Integer valueOfDeposit;

    @Audited
    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "chief_of_customer")
    private String chiefOfCustomer;

    @Audited
    private LocalDate dateOfStart;

    @Audited
    private LocalDate dateOfEnd;

    @Audited
    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_local_address")
    private String workObjectLocalAddress;

    @Audited
    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_storage_address")
    private String workObjectStorageAddress;

    @Audited
    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([+<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: +<>|/-_.,;:«»'()#\"{}№")
    @Column(name = "work_object_comment")
    private String workObjectComment;

    private Integer constructionControlActive;

    private Integer constructionControlClosed;

    private Integer totalCount;

    @Audited
    @Column(name = "work_object_status")
    private WorkObjectStatusENUM workObjectStatusENUM;

    @Audited
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "work_object_chief")
    private Employee workObjectChief;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "workObject", fetch = FetchType.LAZY)
    private List<WorkInstrument> workInstrumentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "workObject", fetch = FetchType.LAZY)
    private List<MeasInstrument> measInstrumentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "workObject", fetch = FetchType.LAZY)
    private List<ConstructionControl> constructionControlList = new ArrayList<>();

    @Audited
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "skeep_work_obj_join",
            joinColumns = @JoinColumn(name = "join_w_obj_id"),
            inverseJoinColumns = @JoinColumn(name = "join_skeep_id"))
    private  List<Employee> storeKeeperList = new ArrayList<>();

    @Audited
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "pto_work_obj_join",
            joinColumns = @JoinColumn(name = "join_w_obj_id"),
            inverseJoinColumns = @JoinColumn(name = "join_pto_id"))
    private List<Employee> PTOList = new ArrayList<>();

    @Audited
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "suppl_work_obj_join",
            joinColumns = @JoinColumn(name = "join_w_obj_id"),
            inverseJoinColumns = @JoinColumn(name = "join_suppl_id"))
    private List<Employee> supplierList = new ArrayList<>();

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

    //TODO Переопределить в метод в контроллере
    public Integer getConstructionControlActive() {
        return constructionControlActive = constructionControlList.stream()
                .filter(e -> e.getConstructionControlStatusENUM().getTitle().contains("Действующий"))
                .toList()
                .size();
    }

    //TODO Переопределить в метод в контроллере
    public Integer getConstructionControlClosed() {
        return constructionControlClosed = constructionControlList.stream()
                .filter(e -> e.getConstructionControlStatusENUM().getTitle().contains("Закрыт"))
                .toList()
                .size();
    }

    //TODO Переопределить в метод в контроллере
    public Integer getTotalCount() {
        return constructionControlActive + constructionControlClosed;
    }

    @Override
    public String toString() {
        return String.format("%s %S", workObjectLot, workObjectOrder);
    }
}
