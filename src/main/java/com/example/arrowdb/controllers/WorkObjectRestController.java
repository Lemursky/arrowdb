package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.WorkObject;
import com.example.arrowdb.services.WorkObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkObjectRestController {

    private final WorkObjectService workObjectService;

    @GetMapping("/api/workobjectView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_VIEW')")
    public WorkObject findWorkObjectById(@PathVariable("id") int id) {
        return workObjectService.findWorkObjectById(id);
    }
}