package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name", unique = true)
    private String roleName;

    @Column(name = "menu_name")
    private String menuName;

    @ManyToMany(mappedBy = "rolesSet", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Users> users = new HashSet<>();

    public void addUsersToRoles() {
        users.forEach(e -> e.getRolesSet().add(this));
    }

    @Override
    public String toString() {
        return roleName;
    }
}
