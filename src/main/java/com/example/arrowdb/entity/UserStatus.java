package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_status")
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_stat_id")
    private Integer userStatId;

    @Column(name = "stat_value")
    private boolean statValue;

    @Column(name = "stat_annotation")
    private String statAnnotation;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "userStatus", fetch = FetchType.LAZY)
    private List<Users> usersList = new ArrayList<>();

    @Override
    public String toString() {
        return statAnnotation;
    }
}
