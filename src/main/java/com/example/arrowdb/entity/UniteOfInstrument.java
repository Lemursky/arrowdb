package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "unit_instr")
@Getter
@Setter
@NoArgsConstructor
public class UniteOfInstrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_instr_id")
    private Integer unitId;

    @Column(name = "unit_instr_name")
    private String unitName;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "uniteOfInstrument", fetch = FetchType.LAZY)
    private List<SpecialCloth> specialClothList = new ArrayList<>();

    public void addUnitToInstrument(){
        specialClothList.forEach(e -> e.setUniteOfInstrument(this));
    }

    @Override
    public String toString() {
        return unitName;
    }
}
