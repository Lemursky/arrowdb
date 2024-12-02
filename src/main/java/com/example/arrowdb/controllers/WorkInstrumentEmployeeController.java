package com.example.arrowdb.controllers;

import com.example.arrowdb.auxiliary.TempIssueDate;
import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.WorkConditionENUM;
import com.example.arrowdb.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WorkInstrumentEmployeeController {

    private final WorkInstrumentService workInstrumentService;
    private final EmployeeService employeeService;
    private final WorkObjectService workObjectService;

    @GetMapping("/general/w_instrument/w_instrument-emp_update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_UPDATE')")
    public String updatePersonalInstrumentEmployee(@PathVariable("id") int id,
                                                   @ModelAttribute WorkInstrument workInstrument,
                                                   @ModelAttribute TempIssueDate tempIssueDate,
                                                   Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList());
        employeeList.remove(employee);
        List<WorkInstrument> workInstrumentList = workInstrumentService
                .findAllWorkInstruments().stream()
                .filter(e -> e.getTechnicalConditionENUM().getTitle().contains("Исправен"))
                .filter(e -> e.getWorkConditionENUM().getTitle().contains("Не закреплен"))
                .toList();
        List<WorkObject> workObjectList = workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList();
        model.addAttribute("workInstrumentList", workInstrumentList);
        model.addAttribute("workObjectList", workObjectList);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeList", employeeList);
        return "stock/w_instrument-emp_update";
    }

    @PostMapping("/general/w_instrument/w_instrumentCreate_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_UPDATE')")
    public String createSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                 @RequestParam @ModelAttribute List<WorkInstrument> workInstrumentListAdd,
                                                 @ModelAttribute WorkObject workObject,
                                                 TempIssueDate tempIssueDate) {
        Employee employee = employeeService.findEmployeeById(id);
        for (WorkInstrument workInstrument : workInstrumentListAdd) {
            workInstrument.setWorkConditionENUM(WorkConditionENUM.INVOLVED);
            workInstrument.setEmployee(employee);
            workInstrument.setWorkObject(workObject);
            workInstrument.setIssueDate(tempIssueDate.getTIssueDate());
            workInstrumentService.saveWorkInstrument(workInstrument);
        }
        return "redirect:/general/w_instrument/w_instrument-emp_update/%d".formatted(id);
    }

    @GetMapping("/general/w_instrument/w_instrumentDelete_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_UPDATE')")
    public String deletePersonalInstrumentEmployee(@PathVariable("id") int id) {
        Integer localId = workInstrumentService.findEmployeeIdByWorkInstId(id);
        WorkInstrument workInstrument = workInstrumentService.findWorkInstrumentById(id);
        workInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
        workInstrument.setWorkObject(null);
        workInstrument.setEmployee(null);
        workInstrument.setIssueDate(null);
        workInstrumentService.saveWorkInstrument(workInstrument);
        return "redirect:/general/w_instrument/w_instrument-emp_update/%d".formatted(localId);
    }

    @GetMapping("/general/w_instrument/w_instrumentDeleteAll_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_UPDATE')")
    public String deleteAllPersonalInstrumentEmployee(@PathVariable("id") int id) {
        Employee employee = employeeService.findEmployeeById(id);
        List<WorkInstrument> workInstrumentListCurrent = employee.getWorkInstrumentList();
        for (WorkInstrument workInstrument: workInstrumentListCurrent) {
            workInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
            workInstrument.setWorkObject(null);
            workInstrument.setEmployee(null);
            workInstrument.setIssueDate(null);
            workInstrumentService.saveWorkInstrument(workInstrument);
        }
        return "redirect:/general/w_instrument/w_instrument-emp_update/%d".formatted(id);
    }

    @PostMapping("/general/w_instrument/w_instrumentChange_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_UPDATE')")
    public String changeAllSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                    Employee employee,
                                                    WorkObject workObject,
                                                    TempIssueDate tempIssueDate) {
        Integer localId = workInstrumentService.findEmployeeIdByWorkInstId(id);
        WorkInstrument workInstrument = workInstrumentService.findWorkInstrumentById(id);
        workInstrument.setWorkObject(workObject);
        workInstrument.setEmployee(employee);
        workInstrument.setIssueDate(tempIssueDate.getTIssueDate());
        workInstrumentService.saveWorkInstrument(workInstrument);
        return "redirect:/general/w_instrument/w_instrument-emp_update/%d".formatted(localId);
    }

    @PostMapping("/general/w_instrument/w_instrumentChangeAll_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_WORK_INSTR_UPDATE')")
    public String changeSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                 Employee employee,
                                                 WorkObject workObject,
                                                 TempIssueDate tempIssueDate) {
        Employee employeeById = employeeService.findEmployeeById(id);
        List<WorkInstrument> workInstrumentListCurrent = employeeById.getWorkInstrumentList();
        for(WorkInstrument workInstrument: workInstrumentListCurrent){
            workInstrument.setEmployee(employee);
            workInstrument.setWorkObject(workObject);
            workInstrument.setIssueDate(tempIssueDate.getTIssueDate());
            workInstrumentService.saveWorkInstrument(workInstrument);
        }
        return "redirect:/general/w_instrument/w_instrument-emp_update/%d".formatted(id);
    }
}