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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class WorkInstrumentController {

    private final EmployeeService employeeService;
    private final WorkObjectService workObjectService;
    private final WorkInstrumentService workInstrumentService;

    @GetMapping("/general/w_instrument/catalog")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_VIEW')")
    public String getWorkInstrumentList(Model model) {
        model.addAttribute("workInstrument", workInstrumentService.findAllWorkInstruments().stream()
                .sorted(Comparator.comparingInt((WorkInstrument::getWorkInstrId)))
                .toList());
        return "stock/w_instrument-catalog";
    }

    @GetMapping("/general/w_instrument")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_VIEW')")
    public String getPersonalInstrumentListEmployee(Model model) {
        model.addAttribute("employee", employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt((Employee::getEmpId)))
                .toList());
        return "stock/w_instrument-com_table";
    }

    @GetMapping("/general/w_instrument/w_instrumentView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_VIEW')")
    public String findWorkInstrumentById(@PathVariable("id") int id,
                                         Model model) {
        model.addAttribute("workInstrument", workInstrumentService.findWorkInstrumentById(id));
        return "stock/w_instrument-view";
    }

    @GetMapping("/general/w_instrument/w_instrumentCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_CREATE')")
    public String createWorkInstrumentForm(@ModelAttribute WorkInstrument workInstrument) {
        return "stock/w_instrument-create";
    }

    @PostMapping("/general/w_instrument/w_instrumentCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_CREATE')")
    public String createWorkInstrument(@Valid @ModelAttribute WorkInstrument workInstrument,
                                       BindingResult bindingResult,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            return "stock/w_instrument-create";
        } else {
            try {
                workInstrument.setTechnicalConditionENUM(TechnicalConditionENUM.OK);
                workInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
                workInstrumentService.saveWorkInstrument(workInstrument);
                return "redirect:/general/w_instrument/catalog";
            } catch (Exception e) {
                model.addAttribute("errorInv", UNIQUE_INSTR_INV);
                return "stock/w_instrument-create";
            }
        }
    }

    @GetMapping("/general/w_instrument/w_instrumentDelete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_DELETE')")
    public String deleteWorkInstrument(@PathVariable("id") int id,
                                       Model model) {
        WorkInstrument workInstrument = workInstrumentService.findWorkInstrumentById(id);
        model.addAttribute("workInstrument", workInstrument);
        model.addAttribute("error", DELETE_INSTRUMENT_MESSAGE);
        if (workInstrument.getEmployee() != null || workInstrument.getWorkObject() != null) {
            return "error/w_instrument-error";
        } else {
            workInstrumentService.deleteWorkInstrumentsById(id);
            return "redirect:/general/w_instrument/catalog";
        }
    }

    @GetMapping("/general/w_instrument/w_instrumentUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_UPDATE')")
    public String updateWorkInstrumentForm(@PathVariable("id") int id,
                                           Model model) {
        WorkInstrument workInstrument = workInstrumentService.findWorkInstrumentById(id);
        if(workInstrument.getTechnicalConditionENUM().getTitle().contains("Списан")){
            return "redirect:/general/w_instrument/w_instrumentView/%d"
                    .formatted(workInstrument.getWorkInstrId());
        }
        List<WorkObject> workObjectList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStat().getStatusName().equals("Действующий")).toList());
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().contains("Действующий")).toList());
        if(!workInstrument.getTechnicalConditionENUM().getTitle().contains("Исправен")){
            workInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
        }
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("workInstrument", workInstrument);
        model.addAttribute("workObjectList", workObjectList);
        if(employeeList.isEmpty() || workObjectList.isEmpty()){
            model.addAttribute("conditionForWork", Arrays.stream(WorkConditionENUM.values())
                    .filter(e -> e.getTitle().contains("Не закреплен (на складе г. Пермь)"))
                    .toList());
        } else {
            model.addAttribute("conditionForWork", WorkConditionENUM.values());
        }
        model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
        return "stock/w_instrument-update";
    }

    @PostMapping("/general/w_instrument/w_instrumentUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_UPDATE')")
    public String updateWorkInstrument(@Valid @ModelAttribute WorkInstrument workInstrument,
                                       BindingResult bindingResult,
                                       Model model) {
        List<WorkObject> workObjectList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStat().getStatusName().equals("Действующий")).toList());
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий")).toList());
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("workObjectList", workObjectList);
            model.addAttribute("conditionForWork", WorkConditionENUM.values());
            model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
            return "stock/w_instrument-update";
        } else {
            if (!workInstrument.getTechnicalConditionENUM().getTitle().contains("Списан")) {
                workInstrument.setCloseDate(null);
            }
            if (!workInstrument.getWorkConditionENUM().getTitle().contains("Закреплен")) {
                workInstrument.setIssueDate(null);
                workInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
            }
            try {
                if (!workInstrument.getWorkConditionENUM().getTitle().contains("Закреплен") ||
                        !workInstrument.getTechnicalConditionENUM().getTitle().contains("Исправен")) {
                    workInstrument.setEmployee(null);
                }
                if (!workInstrument.getWorkConditionENUM().getTitle().contains("Закреплен") ||
                        workInstrument.getTechnicalConditionENUM().getTitle().contains("Списан")) {
                    workInstrument.setWorkObject(null);
                    workInstrument.setEmployee(null);
                }
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("workObjectList", workObjectList);
                model.addAttribute("conditionForWork", WorkConditionENUM.values());
                model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
                workInstrumentService.saveWorkInstrument(workInstrument);
                return "redirect:/general/w_instrument/w_instrumentView/%d"
                        .formatted(workInstrument.getWorkInstrId());
            } catch (Exception e) {
                model.addAttribute("errorInv", UNIQUE_INSTR_INV);
                return "stock/w_instrument-update";
            }
        }
    }
}