package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "unit_instr")
public class UniteOfInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_instr_id")
    private Integer unitId;

    @Column(name = "unit_instr_name")
    private String unitName;

//    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "uniteOfInstrument", fetch = FetchType.LAZY)
//    private List<SpecialCloth> specialClothList = new ArrayList<>();

    @Override
    public String toString() {
        return unitName;
    }
}
