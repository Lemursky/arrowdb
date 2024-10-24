package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.WorkObjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkObjectStatusRepository extends JpaRepository<WorkObjectStatus, Integer> {
    WorkObjectStatus findWorkObjectStatusByStatusName(String name);

    @Query(nativeQuery = true, value = "select * from w_obj_status where status_name=:name")
    Integer findWorkObjectStatusIdByStatusName(String name);
}
