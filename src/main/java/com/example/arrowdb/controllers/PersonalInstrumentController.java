package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.PersonalConditionENUM;
import com.example.arrowdb.enums.TechnicalConditionENUM;
import com.example.arrowdb.services.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class PersonalInstrumentController {

    private final EmployeeService employeeService;
    private final PersonalInstrumentService personalInstrumentService;

    @GetMapping("/general/p_instrument/catalog")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_VIEW')")
    public String getPersonalInstrumentList(Model model) {
        model.addAttribute("personalInstrument", personalInstrumentService
                .findAllPersonalInstruments().stream()
                .sorted(Comparator.comparingInt((PersonalInstrument::getPersonalInstrId)))
                .toList());
        return "stock/p_instrument_catalog";
    }

    @GetMapping("/general/p_instrument")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_VIEW')")
    public String getPersonalInstrumentListEmployee(Model model) {
        model.addAttribute("employee", employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt((Employee::getEmpId)))
                .toList());
        return "stock/p_instrument-com_table";
    }

    @GetMapping("/general/p_instrument/p_instrumentView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_VIEW')")
    public String findPersonalInstrumentById(@PathVariable("id") int id,
                                             Model model) {
        model.addAttribute("personalInstrument", personalInstrumentService
                .findPersonalInstrumentById(id));
        return "stock/p_instrument-view";
    }

    @GetMapping("/general/p_instrument/p_instrumentCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_CREATE')")
    public String createPersonalInstrumentForm(@ModelAttribute PersonalInstrument personalInstrument) {
        return "stock/p_instrument-create";
    }

    @PostMapping("/general/p_instrument/p_instrumentCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_CREATE')")
    public String createPersonalInstrument(@Valid @ModelAttribute PersonalInstrument personalInstrument,
                                           BindingResult bindingResult,
                                           Model model) {
        if (bindingResult.hasErrors()) {
            return "stock/p_instrument-create";
        } else {
            try {
                personalInstrument.setTechnicalConditionENUM(TechnicalConditionENUM.OK);
                personalInstrument.setPersonalConditionENUM(PersonalConditionENUM.NOT_ISSUED);
                personalInstrumentService.savePersonalInstrument(personalInstrument);
                return "redirect:/general/p_instrument/catalog";
            } catch (Exception e) {
                model.addAttribute("errorInv", UNIQUE_INSTR_INV);
                return "stock/p_instrument-create";
            }
        }
    }

    @GetMapping("/general/p_instrument/p_instrumentDelete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_DELETE')")
    public String deletePersonalInstrument(@PathVariable("id") int id,
                                           Model model) {
        PersonalInstrument personalInstrument = personalInstrumentService.findPersonalInstrumentById(id);
        model.addAttribute("personalInstrument", personalInstrument);
        model.addAttribute("error", DELETE_INSTRUMENT_MESSAGE);
        if (personalInstrument.getEmployee() != null) {
            return "error/p_instrument-error";
        } else {
            personalInstrumentService.deletePersonalInstrumentsById(id);
            return "redirect:/general/p_instrument/catalog";
        }
    }

    @GetMapping("/general/p_instrument/p_instrumentUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String updateInstrumentForm(@PathVariable("id") int id, Model model) {
        PersonalInstrument personalInstrument = personalInstrumentService.findPersonalInstrumentById(id);
        if (personalInstrument.getTechnicalConditionENUM().getTitle().contains("Списан")) {
            return "redirect:/general/p_instrument/p_instrumentView/%d"
                    .formatted(personalInstrument.getPersonalInstrId());
        }
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .toList());
        if (!personalInstrument.getTechnicalConditionENUM().getTitle().contains("Исправен")) {
            personalInstrument.setPersonalConditionENUM(PersonalConditionENUM.NOT_ISSUED);
        }
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("personalInstrument", personalInstrument);
        if(employeeList.isEmpty()) {
            model.addAttribute("conditionForPersonal", Arrays.stream(PersonalConditionENUM.values())
                    .filter(e -> e.getTitle().contains("Не выдан (на складе)")).toList());
        } else {
            model.addAttribute("conditionForPersonal", PersonalConditionENUM.values());
        }
        model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
        return "stock/p_instrument-update";
    }

    @PostMapping("/general/p_instrument/p_instrumentUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String updateInstrument(@Valid @ModelAttribute PersonalInstrument personalInstrument,
                                   @NotNull BindingResult bindingResult,
                                   Model model) {
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий")).toList());
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
            return "stock/p_instrument-update";
        } else {
            if (!personalInstrument.getTechnicalConditionENUM().getTitle().contains("Списан")) {
                personalInstrument.setCloseDate(null);
            }
            if (!personalInstrument.getPersonalConditionENUM().getTitle().contains("Выдан")) {
                personalInstrument.setIssueDate(null);
                personalInstrument.setPersonalConditionENUM(PersonalConditionENUM.NOT_ISSUED);
            }
            if (!personalInstrument.getTechnicalConditionENUM().getTitle().contains("Исправен")) {
                personalInstrument.setIssueDate(null);
                personalInstrument.setPersonalConditionENUM(PersonalConditionENUM.NOT_ISSUED);
            }
            if (!personalInstrument.getPersonalConditionENUM().getTitle().contains("Выдан") ||
                    !personalInstrument.getTechnicalConditionENUM().getTitle().contains("Исправен")) {
                personalInstrument.setEmployee(null);
            }
            try {
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("conditionForPersonal", PersonalConditionENUM.values());
                model.addAttribute("conditionForTechn", TechnicalConditionENUM.values());
                personalInstrumentService.savePersonalInstrument(personalInstrument);
                return "redirect:/general/p_instrument/p_instrumentView/%d"
                        .formatted(personalInstrument.getPersonalInstrId());
            } catch (Exception e) {
                model.addAttribute("errorInv", new StringBuilder(UNIQUE_INSTR_INV));
                return "stock/p_instrument-update";
            }
        }
    }
}