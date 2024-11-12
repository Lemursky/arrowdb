package com.example.arrowdb.entity;

import com.example.arrowdb.enums.SpecialClothStatusENUM;
import com.example.arrowdb.enums.UniteOfInstrumentENUM;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "special_cloth")
public class SpecialCloth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spec_cloth_id")
    private Integer specClothId;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 200, message = "Кол-во символов от 2 до 200")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "spec_cloth_name", unique=true)
    private String specClothName;

    @Size(max = 1000, message = "Кол-во символов максимум 1000")
    @Pattern(regexp = "([<>|/-_.,;:«»'()#\"{}№\\n\\-\\dа-яА-Я-a-zA-Z\\s]+)?",
            message = "только - алфавит: Кириллица, Латинский; цифры; символы: <>|/-_.,;:«»'()#\"{}№")
    @Column(name = "spec_cloth_comment")
    private String specClothComment;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "specialCloth", fetch = FetchType.LAZY)
    private List<SpecialClothEmployee> specialClothEmployeeList = new ArrayList<>();

    @Column(name = "spec_cloth_status")
    private SpecialClothStatusENUM specialClothStatusENUM;

    @Column(name = "unit_of_sc")
    private UniteOfInstrumentENUM uniteOfInstrumentENUM;

    public void setSpecClothName(String specClothName) {
        try {
            this.specClothName = specClothName.substring(0, 1).toUpperCase() + specClothName.substring(1).trim();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public String toString() {
        return specClothName;
    }
}