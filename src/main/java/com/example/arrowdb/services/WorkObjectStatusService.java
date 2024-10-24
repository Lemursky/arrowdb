package com.example.arrowdb.services;

import com.example.arrowdb.entity.WorkObjectStatus;

import java.util.List;

public interface WorkObjectStatusService {
    WorkObjectStatus findWorkObjectStatusByStatusName(String name);
    WorkObjectStatus findWorkObjectStatusBiId(Integer id);
    List<WorkObjectStatus> findAllWorkObjectStatus();
    Integer findWorkObjectStatusIdByStatusName(String name);
}
