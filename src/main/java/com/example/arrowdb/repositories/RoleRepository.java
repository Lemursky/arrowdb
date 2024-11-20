package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Roles;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "select * from roles  where menu_name=:name")
    List<Roles> findRolesByMenuName(String name);

    @Transactional(readOnly = true)
    Roles findRolesByRoleName(String name);

}
