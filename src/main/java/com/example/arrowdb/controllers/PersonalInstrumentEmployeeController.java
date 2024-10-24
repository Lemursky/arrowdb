package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.services.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PersonalInstrumentEmployeeController {

    private final PersonalInstrumentService personalInstrumentService;
    private final EmployeeService employeeService;
    private final ConditionForPersonalService conditionForPersonalService;

    @GetMapping("/general/p_instrument/p_instrument-emp_update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String updatePersonalInstrumentEmployee(@PathVariable("id") int id,
                                                   @ModelAttribute PersonalInstrument personalInstrument,
                                                   @NotNull Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        List<PersonalInstrument> personalInstrumentList = personalInstrumentService
                .findAllPersonalInstruments().stream()
                .filter(e -> e.getConditionForTechn().getTConditionName().equals("Исправен"))
                .filter(e -> e.getConditionForPersonal().getPConditionName().equals("Не выдан"))
                .toList();
        model.addAttribute("personalInstrumentList", personalInstrumentList);
        model.addAttribute("employee", employee);
        return "stock/p_instrument-emp_update";
    }

    @GetMapping("/general/p_instrument/p_instrumentDelete_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String deletePersonalInstrumentEmployee(@PathVariable("id") int id) {
        Integer localId = personalInstrumentService.findPersonalInstIdByEmployeeId(id);
        PersonalInstrument personalInstrument = personalInstrumentService.findPersonalInstrumentById(id);
        ConditionForPersonal conditionForPersonal = conditionForPersonalService
                .findConditionForPersonalBypConditionName("Не выдан");
        personalInstrument.setConditionForPersonal(conditionForPersonal);
        personalInstrument.setEmployee(null);
        personalInstrument.setIssueDate(null);
        personalInstrumentService.savePersonalInstrument(personalInstrument);
        return "redirect:/general/p_instrument/p_instrument-emp_update/%d".formatted(localId);
    }

    @PostMapping("/general/p_instrument/p_instrumentCreate_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String createSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                 @ModelAttribute PersonalInstrument personalInstrument,
                                                 @NotNull Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        ConditionForPersonal conditionForPersonal = conditionForPersonalService
                .findConditionForPersonalBypConditionName("Выдан");
        personalInstrument.setConditionForPersonal(conditionForPersonal);
        personalInstrument.setEmployee(employee);
        personalInstrumentService.savePersonalInstrument(personalInstrument);
        return "redirect:/general/p_instrument/p_instrument-emp_update/%d".formatted(id);
    }
}