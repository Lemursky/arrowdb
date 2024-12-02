package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.WorkObject;
import com.example.arrowdb.history.WorkInstrumentAUD;
import com.example.arrowdb.history.WorkObjectAUD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WorkObjectAUDRepository extends JpaRepository<WorkObjectAUD, Integer> {

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "select * from history.work_object_aud where w_obj_id=:id order by rev desc")
    List<WorkObjectAUD> findAllWorkObjectAUDById(Integer id);
}
