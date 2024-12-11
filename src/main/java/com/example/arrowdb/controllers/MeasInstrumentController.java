package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.TechnicalConditionENUM;
import com.example.arrowdb.enums.WorkConditionENUM;
import com.example.arrowdb.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.example.arrowdb.auxiliary.Message.*;

@Controller
@RequiredArgsConstructor
public class MeasInstrumentController {

    private final EmployeeService employeeService;
    private final WorkObjectService workObjectService;
    private final MeasInstrumentService measInstrumentService;
    private final DepartmentService departmentService;

    @GetMapping("/general/m_instrument/catalog")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_VIEW')")
    public String getMeasInstrumentList(Model model) {
        model.addAttribute("measInstrument", measInstrumentService.findAllMeasInstruments().stream()
                .sorted(Comparator.comparingInt((MeasInstrument::getMeasInstrId)))
                .toList());
        return "stock/m_instrument-catalog";
    }

    @GetMapping("/general/m_instrument")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_VIEW')")
    public String getPersonalInstrumentListEmployee(Model model) {
        model.addAttribute("employee", employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt((Employee::getEmpId)))
                .toList());
        return "stock/m_instrument-com_table";
    }

    @GetMapping("/general/m_instrument/m_instrumentView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_VIEW')")
    public String findMeasInstrumentById(@PathVariable("id") int id,
                                         Model model) {
        model.addAttribute("measInstrument", measInstrumentService.findMeasInstrumentById(id));
        return "stock/m_instrument-view";
    }

    @GetMapping("/general/m_instrument/m_instrumentCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_CREATE')")
    public String createMeasInstrumentForm(@ModelAttribute MeasInstrument measInstrument,
                                           Model model) {
        List<Department> departmentList = departmentService.findAllDepartments();
        model.addAttribute("departmentList", departmentList);
        return "stock/m_instrument-create";
    }

    @PostMapping("/general/m_instrument/m_instrumentCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_CREATE')")
    public String createMeasInstrument(@Valid @ModelAttribute MeasInstrument measInstrument,
                                       BindingResult bindingResult,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            List<Department> departmentList = departmentService.findAllDepartments();
            model.addAttribute("departmentList", departmentList);
            return "stock/m_instrument-create";
        } else {
            try {
                measInstrument.setTechnicalConditionENUM(TechnicalConditionENUM.OK);
                measInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
                model.addAttribute("departmentList", departmentService.findAllDepartments());
                measInstrumentService.saveMeasInstrument(measInstrument);
                return "redirect:/general/m_instrument/catalog";
            } catch (Exception e) {
                model.addAttribute("errorInv", UNIQUE_INSTR_INV);
                return "stock/m_instrument-create";
            }
        }
    }

    @GetMapping("/general/m_instrument/m_instrumentDelete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_DELETE')")
    public String deleteMeasInstrument(@PathVariable("id") int id, Model model) {
        MeasInstrument measInstrument = measInstrumentService.findMeasInstrumentById(id);
        model.addAttribute("measInstrument", measInstrument);
        model.addAttribute("error", DELETE_INSTRUMENT_MESSAGE);
        if (measInstrument.getEmployee() != null || measInstrument.getWorkObject() != null) {
            return "error/m_instrument-error";
        } else {
            measInstrumentService.deleteMeasInstrumentById(id);
            return "redirect:/general/m_instrument/catalog";
        }
    }

    @GetMapping("/general/m_instrument/m_instrumentUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_UPDATE')")
    public String updateMeasInstrumentForm(@PathVariable("id") int id,
                                           Model model) {
        MeasInstrument measInstrument = measInstrumentService.findMeasInstrumentById(id);
        if(measInstrument.getTechnicalConditionENUM().getTitle().contains("Списан")){
            return "redirect:/general/m_instrument/m_instrumentView/%d"
                    .formatted(measInstrument.getMeasInstrId());
        }
        List<WorkObject> workObjectList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStatusENUM().getTitle().contains("Действующий")).toList());
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().contains("Действующий")).toList());
        if(!measInstrument.getTechnicalConditionENUM().getTitle().contains("Исправен")){
            measInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
        }
        List<Department> departmentList = departmentService.findAllDepartments();
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("workObjectList", workObjectList);
        model.addAttribute("measInstrument", measInstrument);
        model.addAttribute("departmentList", departmentList);
        if(employeeList.isEmpty() || workObjectList.isEmpty()){
            model.addAttribute("conditionForWork", Arrays.stream(WorkConditionENUM.values())
                    .filter(e -> e.getTitle().contains("Не закреплен (на складе г. Пермь)"))
                    .toList());
        } else {
            model.addAttribute("conditionForWork", WorkConditionENUM.values());
        }
        model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
        return "stock/m_instrument-update";
    }

    @PostMapping("/general/m_instrument/m_instrumentUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_UPDATE')")
    public String updateMeasInstrument(@Valid @ModelAttribute MeasInstrument measInstrument,
                                       BindingResult bindingResult,
                                       Model model) {
        List<WorkObject> workObjectList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStatusENUM().getTitle().contains("Действующий")).toList());
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий")).toList());
        List<Department> departmentList = departmentService.findAllDepartments();
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("workObjectList", workObjectList);
            model.addAttribute("departmentList", departmentList);
            model.addAttribute("conditionForWork", WorkConditionENUM.values());
            model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
            return "stock/m_instrument-update";
        } else {
            if (!measInstrument.getTechnicalConditionENUM().getTitle().contains("Списан")) {
                measInstrument.setCloseDate(null);
            }
            if (!measInstrument.getWorkConditionENUM().getTitle().contains("Закреплен")) {
                measInstrument.setIssueDate(null);
                measInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
            }
            try {
                if (!measInstrument.getWorkConditionENUM().getTitle().contains("Закреплен") ||
                        !measInstrument.getTechnicalConditionENUM().getTitle().contains("Исправен")) {
                    measInstrument.setEmployee(null);
                }
                if (!measInstrument.getWorkConditionENUM().getTitle().contains("Закреплен") ||
                        measInstrument.getTechnicalConditionENUM().getTitle().contains("Списан")) {
                    measInstrument.setWorkObject(null);
                    measInstrument.setEmployee(null);
                }
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("workObjectList", workObjectList);
                model.addAttribute("departmentList", departmentList);
                model.addAttribute("conditionForWork", WorkConditionENUM.values());
                model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
                measInstrumentService.saveMeasInstrument(measInstrument);
                return "redirect:/general/m_instrument/m_instrumentView/%d"
                        .formatted(measInstrument.getMeasInstrId());
            } catch (Exception e) {
                model.addAttribute("errorInv", UNIQUE_INSTR_INV);
                return "stock/m_instrument-update";
            }
        }
    }
}