package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

    @Query(nativeQuery = true, value = "select * from roles  where menu_name=:name")
    List<Roles> findRolesByMenuName(String name);

}
