package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class WorkInstrumentController {

    private final EmployeeService employeeService;
    private final WorkObjectService workObjectService;
    private final WorkInstrumentService workInstrumentService;
    private final ConditionForWorkService conditionForWorkService;
    private final ConditionForTechnService conditionForTechnService;

    @GetMapping("/general/w_instrument/catalog")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_VIEW')")
    public String getWorkInstrumentList(Model model) {
        List<WorkInstrument> workInstrument = workInstrumentService.findAllWorkInstruments().stream()
                .sorted(Comparator.comparingInt((WorkInstrument::getWorkInstrId)))
                .toList();
        model.addAttribute("workInstrument", workInstrument);
        return "stock/w_instrument-catalog";
    }

    @GetMapping("/general/w_instrument")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_VIEW')")
    public String getPersonalInstrumentListEmployee(Model model) {
        List<Employee> employee = employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt((Employee::getEmpId)))
                .toList();
        model.addAttribute("employee", employee);
        return "stock/w_instrument-com_table";
    }

    @GetMapping("/general/w_instrument/w_instrumentView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_VIEW')")
    public String findWorkInstrumentById(@PathVariable("id") int id,
                                         Model model) {
        WorkInstrument workInstrument = workInstrumentService.findWorkInstrumentById(id);
        model.addAttribute("workInstrument", workInstrument);
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
                workInstrument.setConditionForTechn(conditionForTechnService
                        .findConditionForTechnBytConditionName("Исправен"));
                workInstrument.setConditionForWork(conditionForWorkService
                        .findConditionForWorkBywConditionName("Не закреплен"));
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
        if(workInstrument.getConditionForTechn().getTConditionName().equals("Списан")){
            return "redirect:/general/w_instrument/w_instrumentView/%d"
                    .formatted(workInstrument.getWorkInstrId());
        }
        List<WorkObject> workObjectList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStat().getStatusName().equals("Действующий")).toList());
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий")).toList());
        List<ConditionForWork> conditionForWork = conditionForWorkService.findAllConditionForWork();
        List<ConditionForTechn> conditionForTechn = conditionForTechnService.findAllConditionForTechn();
        if(!workInstrument.getConditionForTechn().getTConditionName().equals("Исправен")){
            workInstrument.setConditionForWork(conditionForWorkService.
                    findConditionForWorkBywConditionName("Не закреплен"));
        }
        if (workObjectList.isEmpty() || employeeList.isEmpty()) {
            conditionForWork.remove(conditionForWorkService.findConditionForWorkBywConditionName("Закреплен"));
        }
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("workInstrument", workInstrument);
        model.addAttribute("workObjectList", workObjectList);
        model.addAttribute("conditionForWork", conditionForWork);
        model.addAttribute("conditionForTechn", conditionForTechn);
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
        List<ConditionForWork> conditionForWork = conditionForWorkService.findAllConditionForWork();
        List<ConditionForTechn> conditionForTechn = conditionForTechnService.findAllConditionForTechn();
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("workObjectList", workObjectList);
            model.addAttribute("conditionForWork", conditionForWork);
            model.addAttribute("conditionForTechn", conditionForTechn);
            return "stock/w_instrument-update";
        } else {
            if (!workInstrument.getConditionForTechn().getTConditionName().equals("Списан")) {
                workInstrument.setCloseDate(null);
            }
            if (!workInstrument.getConditionForWork().getWConditionName().equals("Закреплен")) {
                workInstrument.setIssueDate(null);
                workInstrument.setConditionForWork(conditionForWorkService
                        .findConditionForWorkBywConditionName("Не закреплен"));
            }
            try {
                if (!workInstrument.getConditionForWork().getWConditionName().equals("Закреплен") ||
                        !workInstrument.getConditionForTechn().getTConditionName().equals("Исправен")) {
                    workInstrument.setEmployee(null);
                }
                if (!workInstrument.getConditionForWork().getWConditionName().equals("Закреплен") ||
                        workInstrument.getConditionForTechn().getTConditionName().equals("Списан")) {
                    workInstrument.setWorkObject(null);
                    workInstrument.setEmployee(null);
                }
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("workObjectList", workObjectList);
                model.addAttribute("conditionForWork", conditionForWork);
                model.addAttribute("conditionForTechn", conditionForTechn);
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