package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.PersonalConditionENUM;
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

    @GetMapping("/general/p_instrument/p_instrument-emp_update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String updatePersonalInstrumentEmployee(@PathVariable("id") int id,
                                                   @ModelAttribute PersonalInstrument personalInstrument,
                                                   @NotNull Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        List<PersonalInstrument> personalInstrumentList = personalInstrumentService
                .findAllPersonalInstruments().stream()
                .filter(e -> e.getTechnicalConditionENUM().getTitle().contains("Исправен"))
                .filter(e -> e.getPersonalConditionENUM().getTitle().contains("Не выдан"))
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
        personalInstrument.setPersonalConditionENUM(PersonalConditionENUM.NOT_ISSUED);
        personalInstrument.setEmployee(null);
        personalInstrument.setIssueDate(null);
        personalInstrumentService.savePersonalInstrument(personalInstrument);
        return "redirect:/general/p_instrument/p_instrument-emp_update/%d".formatted(localId);
    }

    @PostMapping("/general/p_instrument/p_instrumentCreate_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String createSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                 @ModelAttribute PersonalInstrument personalInstrument) {
        Employee employee = employeeService.findEmployeeById(id);
        personalInstrument.setPersonalConditionENUM(PersonalConditionENUM.ISSUED);
        personalInstrument.setEmployee(employee);
        personalInstrumentService.savePersonalInstrument(personalInstrument);
        return "redirect:/general/p_instrument/p_instrument-emp_update/%d".formatted(id);
    }
}