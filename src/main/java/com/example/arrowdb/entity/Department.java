package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_id")
    private Integer depId;

    @Column(name = "dep_name")
    private String depName;

    @Column(name = "dep_annotation")
    private String depAnnotation;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "department", fetch = FetchType.LAZY)
    private List<MeasInstrument> measInstrumentList = new ArrayList<>();

    @Override
    public String toString() {
        return depName;
    }



}
