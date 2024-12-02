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
public class MeasInstrumentEmployeeController {

    private final MeasInstrumentService measInstrumentService;
    private final EmployeeService employeeService;
    private final WorkObjectService workObjectService;

    @GetMapping("/general/m_instrument/m_instrument-emp_update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_UPDATE')")
    public String updatePersonalInstrumentEmployee(@PathVariable("id") int id,
                                                   @ModelAttribute MeasInstrument measInstrument,
                                                   @ModelAttribute TempIssueDate tempIssueDate,
                                                   Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList());
        employeeList.remove(employee);
        List<MeasInstrument> measInstrumentList = measInstrumentService
                .findAllMeasInstruments().stream()
                .filter(e -> e.getTechnicalConditionENUM().getTitle().contains("Исправен"))
                .filter(e -> e.getWorkConditionENUM().getTitle().contains("Не закреплен"))
                .toList();
        List<WorkObject> workObjectList = workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList();
        model.addAttribute("measInstrumentList", measInstrumentList);
        model.addAttribute("workObjectList", workObjectList);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeList", employeeList);
        return "stock/m_instrument-emp_update";
    }

    @PostMapping("/general/m_instrument/m_instrumentCreate_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_UPDATE')")
    public String createSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                 @RequestParam @ModelAttribute List<MeasInstrument> measInstrumentListAdd,
                                                 @ModelAttribute WorkObject workObject,
                                                 TempIssueDate tempIssueDate) {
        Employee employee = employeeService.findEmployeeById(id);
        for(MeasInstrument measInstrument: measInstrumentListAdd){
            measInstrument.setWorkConditionENUM(WorkConditionENUM.INVOLVED);
            measInstrument.setEmployee(employee);
            measInstrument.setWorkObject(workObject);
            measInstrument.setIssueDate(tempIssueDate.getTIssueDate());
            measInstrumentService.saveMeasInstrument(measInstrument);
        }
        return "redirect:/general/m_instrument/m_instrument-emp_update/%d".formatted(id);
    }

    @GetMapping("/general/m_instrument/m_instrumentDelete_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_UPDATE')")
    public String deletePersonalInstrumentEmployee(@PathVariable("id") int id) {
        Integer localId = measInstrumentService.findEmployeeIdByMeasInstId(id);
        MeasInstrument measInstrument = measInstrumentService.findMeasInstrumentById(id);
        measInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
        measInstrument.setWorkObject(null);
        measInstrument.setEmployee(null);
        measInstrument.setIssueDate(null);
        measInstrumentService.saveMeasInstrument(measInstrument);
        return "redirect:/general/m_instrument/m_instrument-emp_update/%d".formatted(localId);
    }

    @GetMapping("/general/m_instrument/m_instrumentDeleteAll_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_UPDATE')")
    public String deleteAllPersonalInstrumentEmployee(@PathVariable("id") int id) {
        Employee employee = employeeService.findEmployeeById(id);
        List<MeasInstrument> measInstrumentListCurrent = employee.getMeasInstrumentList();
        for(MeasInstrument measInstrument: measInstrumentListCurrent){
            measInstrument.setWorkConditionENUM(WorkConditionENUM.NOT_INVOLVED);
            measInstrument.setWorkObject(null);
            measInstrument.setEmployee(null);
            measInstrument.setIssueDate(null);
            measInstrumentService.saveMeasInstrument(measInstrument);
        }
        return "redirect:/general/m_instrument/m_instrument-emp_update/%d".formatted(id);
    }

    @PostMapping("/general/m_instrument/m_instrumentChange_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_UPDATE')")
    public String changeAllSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                    Employee employee,
                                                    WorkObject workObject,
                                                    TempIssueDate tempIssueDate) {
        Integer localId = measInstrumentService.findEmployeeIdByMeasInstId(id);
        MeasInstrument measInstrument = measInstrumentService.findMeasInstrumentById(id);
        measInstrument.setWorkObject(workObject);
        measInstrument.setEmployee(employee);
        measInstrument.setIssueDate(tempIssueDate.getTIssueDate());
        measInstrumentService.saveMeasInstrument(measInstrument);
        return "redirect:/general/m_instrument/m_instrument-emp_update/%d".formatted(localId);
    }

    @PostMapping("/general/m_instrument/m_instrumentChangeAll_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_MEAS_INSTR_UPDATE')")
    public String changeSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                 Employee employee,
                                                 WorkObject workObject,
                                                 TempIssueDate tempIssueDate) {
        Employee employeeById = employeeService.findEmployeeById(id);
        List<MeasInstrument> measInstrumentListCurrent = employeeById.getMeasInstrumentList();
        for(MeasInstrument measInstrument: measInstrumentListCurrent){
            measInstrument.setWorkConditionENUM(WorkConditionENUM.INVOLVED);
            measInstrument.setEmployee(employee);
            measInstrument.setWorkObject(workObject);
            measInstrument.setIssueDate(tempIssueDate.getTIssueDate());
            measInstrumentService.saveMeasInstrument(measInstrument);
        }
        return "redirect:/general/m_instrument/m_instrument-emp_update/%d".formatted(id);
    }
}
