package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
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
import java.util.Comparator;
import java.util.List;

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class PersonalInstrumentController {

    private final EmployeeService employeeService;
    private final PersonalInstrumentService personalInstrumentService;
    private final ConditionForPersonalService conditionForPersonalService;
    private final ConditionForTechnService conditionForTechnService;

    @GetMapping("/general/p_instrument/catalog")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_VIEW')")
    public String getPersonalInstrumentList(Model model) {
        List<PersonalInstrument> personalInstrument = personalInstrumentService.findAllPersonalInstruments().stream()
                .sorted(Comparator.comparingInt((PersonalInstrument::getPersonalInstrId)))
                .toList();
        model.addAttribute("personalInstrument", personalInstrument);
        return "stock/p_instrument_catalog";
    }

    @GetMapping("/general/p_instrument")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_VIEW')")
    public String getPersonalInstrumentListEmployee(Model model) {
        List<Employee> employee = employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .sorted(Comparator.comparingInt((Employee::getEmpId)))
                .toList();
        model.addAttribute("employee", employee);
        return "stock/p_instrument-com_table";
    }

    @GetMapping("/general/p_instrument/p_instrumentView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_VIEW')")
    public String findPersonalInstrumentById(@PathVariable("id") int id,
                                             Model model) {
        PersonalInstrument personalInstrument = personalInstrumentService.findPersonalInstrumentById(id);
        model.addAttribute("personalInstrument", personalInstrument);
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
                personalInstrument.setConditionForTechn(conditionForTechnService.findConditionForTechnBytConditionName("Исправен"));
                personalInstrument.setConditionForPersonal(conditionForPersonalService.findConditionForPersonalBypConditionName("Не выдан"));
                personalInstrumentService.savePersonalInstrument(personalInstrument);
                return "redirect:/general/p_instrument/catalog";
            } catch (Exception e) {
                model.addAttribute("errorInv", new StringBuilder(UNIQUE_INSTR_INV));
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
        model.addAttribute("error", new StringBuilder(DELETE_INSTRUMENT_MESSAGE));
        if (personalInstrument.getEmployee() != null) {
            return "error/p_instrument-error";
        } else {
            personalInstrumentService.deletePersonalInstrumentsById(id);
            return "redirect:/general/p_instrument/catalog";
        }
    }

    @GetMapping("/general/p_instrument/p_instrumentUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String updateInstrumentForm(@PathVariable("id") int id,
                                       Model model) {
        PersonalInstrument personalInstrument = personalInstrumentService.findPersonalInstrumentById(id);
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий")).toList());
        List<ConditionForPersonal> conditionForPersonal = conditionForPersonalService.findAllConditionForPersonal();
        List<ConditionForTechn> conditionForTechn = conditionForTechnService.findAllConditionForTechn();
        if (employeeList.isEmpty()) {
            conditionForPersonal.remove(conditionForPersonalService.findConditionForPersonalBypConditionName("Выдан"));
        }
        model.addAttribute("personalInstrument", personalInstrument);
        model.addAttribute("conditionForPersonal", conditionForPersonal);
        model.addAttribute("conditionForTechn", conditionForTechn);
        model.addAttribute("employeeList", employeeList);
        return "stock/p_instrument-update";
    }

    @PostMapping("/general/p_instrument/p_instrumentUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_PERS_INSTR_UPDATE')")
    public String updateInstrument(@Valid @ModelAttribute PersonalInstrument personalInstrument,
                                   @NotNull BindingResult bindingResult,
                                   Model model) {
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий")).toList());
        List<ConditionForPersonal> conditionForPersonal = conditionForPersonalService.findAllConditionForPersonal();
        List<ConditionForTechn> conditionForTechn = conditionForTechnService.findAllConditionForTechn();
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("conditionForTechn", conditionForTechn);
            return "stock/p_instrument-update";
        } else {
            if (!personalInstrument.getConditionForTechn().getTConditionName().equals("Списан")) {
                personalInstrument.setCloseDate(null);
            }
            if (!personalInstrument.getConditionForPersonal().getPConditionName().equals("Выдан")) {
                personalInstrument.setIssueDate(null);
                personalInstrument.setConditionForPersonal(conditionForPersonalService
                        .findConditionForPersonalBypConditionName("Не выдан"));
            }
            if (!personalInstrument.getConditionForTechn().getTConditionName().equals("Исправен")) {
                personalInstrument.setIssueDate(null);
                personalInstrument.setConditionForPersonal(conditionForPersonalService
                        .findConditionForPersonalBypConditionName("Не выдан"));
            }
            if (!personalInstrument.getConditionForPersonal().getPConditionName().equals("Выдан") ||
                    !personalInstrument.getConditionForTechn().getTConditionName().equals("Исправен")) {
                personalInstrument.setEmployee(null);
            }
            try {
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("conditionForPersonal", conditionForPersonal);
                model.addAttribute("conditionForTechn", conditionForTechn);
                personalInstrumentService.savePersonalInstrument(personalInstrument);
                return "redirect:/general/p_instrument/p_instrumentView/%d".formatted(personalInstrument.getPersonalInstrId());
            } catch (Exception e) {
                model.addAttribute("errorInv", new StringBuilder(UNIQUE_INSTR_INV));
                return "stock/p_instrument-update";
            }
        }
    }
}