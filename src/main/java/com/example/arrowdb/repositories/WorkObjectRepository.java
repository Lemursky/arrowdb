package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.WorkInstrument;
import com.example.arrowdb.entity.WorkObject;
import com.example.arrowdb.entity.WorkObjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkObjectRepository extends JpaRepository<WorkObject, Integer> {

    @Query(nativeQuery = true, value = "select * from work_object where work_object_status=:status1 " +
            "or work_object_status=:status2 or work_object_status=:status3 order by w_obj_id")
    List<WorkObject> findWorkObjectByStatus(Integer status1, Integer status2, Integer status3);

}
