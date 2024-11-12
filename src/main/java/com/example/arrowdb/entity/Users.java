package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "join_user_id"),
            inverseJoinColumns = @JoinColumn(name = "join_role_id"))
    private Set<Roles> rolesSet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_stat")
    private UserStatus userStatus;

    public void addRolesToUsers() {
        rolesSet.forEach(e -> e.getUsers().add(this));
    }

    @Override
    public String toString() {
        return userName;
    }
}