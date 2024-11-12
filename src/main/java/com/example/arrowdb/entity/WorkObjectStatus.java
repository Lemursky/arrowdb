package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "w_obj_status")
public class WorkObjectStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_obj_stat_id")
    private Integer wObjStatId;

    @Column(name = "status_name")
    private String statusName;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "workObjectStat", fetch = FetchType.LAZY)
    private List<WorkObject> workObjectList = new ArrayList<>();

    @Override
    public String toString() {
        return statusName;
    }

}