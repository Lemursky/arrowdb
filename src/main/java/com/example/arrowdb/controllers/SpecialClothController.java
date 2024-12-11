package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.SpecialClothStatusENUM;
import com.example.arrowdb.enums.UniteOfInstrumentENUM;
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

import java.util.*;

import static com.example.arrowdb.auxiliary.Message.*;

@Controller
@RequiredArgsConstructor
public class SpecialClothController {

    private final SpecialClothService specialClothService;
    private final EmployeeService employeeService;
    private final SpecialClothEmployeeService specialClothEmployeeService;

    @GetMapping("/general/s_cloth/catalog")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_VIEW')")
    public String getAllSpecialClothList(@NotNull Model model){
        model.addAttribute("specialClothList", specialClothService.findAllSpecialCloths()
                .stream()
                .sorted(Comparator.comparingInt((SpecialCloth::getSpecClothId)))
                .toList());
        return "stock/spec_cloth-catalog";
    }

    @GetMapping("/general/s_cloth")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_VIEW')")
    public String getAllSpecialClothListEmployee(@NotNull Model model) {
        List<Employee> employee = employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt((Employee::getEmpId)))
                .toList();
        model.addAttribute("employee", employee);
        return "stock/spec_cloth-com_table";
    }

    @GetMapping("/general/s_cloth/s_clothView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_VIEW')")
    public String findSpecialClothById(@PathVariable("id") int id,
                                       @NotNull Model model) {
        List<Integer> idList = specialClothEmployeeService.findAllEmployeeBySpecialClothEmployeeId(id);
        Employee employee;
        List<Employee> employeeList = new ArrayList<>();
        for (Integer integer : idList) {
            employee = employeeService.findEmployeeById(integer);
            employeeList.add(employee);
        }
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("specialCloth", specialClothService.findSpecialClothById(id));
        return "stock/spec_cloth-view";
    }

    @GetMapping("/general/s_cloth/s_clothCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_CREATE')")
    public String createSpecialClothForm(@ModelAttribute SpecialCloth specialCloth,
                                         @NotNull Model model) {
        model.addAttribute("uniteOfInstrument", UniteOfInstrumentENUM.values());
        return "stock/spec_cloth-create";
    }

    @PostMapping("/general/s_cloth/s_clothCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_CREATE')")
    public String createSpecialCloth(@Valid SpecialCloth specialCloth,
                                     @NotNull BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("uniteOfInstrument", UniteOfInstrumentENUM.values());
            return "stock/spec_cloth-create";
        } else {
            try {
                specialCloth.setSpecialClothStatusENUM(SpecialClothStatusENUM.ACTIVE);
                model.addAttribute("specialCloth", specialCloth);
                model.addAttribute("uniteOfInstrument", UniteOfInstrumentENUM.values());
                specialClothService.saveSpecialCloth(specialCloth);
                return "redirect:/general/s_cloth/catalog";
            } catch (Exception e) {
                model.addAttribute("errorName", new StringBuilder(UNIQUE_SCLOTH_NAME));
                return "stock/spec_cloth-create";
            }
        }
    }

    @GetMapping("/general/s_cloth/s_clothDelete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_DELETE')")
    public String deleteSpecialCloth(@PathVariable("id") int id,
                                     Model model) {
        SpecialCloth specialCloth = specialClothService.findSpecialClothById(id);
        List<Integer> idList = specialClothEmployeeService.findAllEmployeeBySpecialClothEmployeeId(id);
        Employee employee;
        List<Employee> employeeList = new ArrayList<>();
        for (Integer integer : idList) {
            employee = employeeService.findEmployeeById(integer);
            employeeList.add(employee);
        }
        model.addAttribute("specialCloth", specialCloth);
        model.addAttribute("employeeList", employeeList);
        try{
            specialClothService.deleteSpecialClothById(id);
            return "redirect:/general/s_cloth/catalog";
        } catch (Exception e){
            model.addAttribute("error", new StringBuilder(DELETE_OR_CHANGE_STATUS_SCLOTH_MESSAGE));
            return "error/s_cloth-error";
        }
    }

    @GetMapping("/general/s_cloth/s_clothUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_UPDATE')")
    public String updateSpecialClothForm(@PathVariable("id") int id,
                                         @NotNull Model model) {
        model.addAttribute("specialCloth", specialClothService.findSpecialClothById(id));
        model.addAttribute("specialClothStatus", SpecialClothStatusENUM.values());
        model.addAttribute("uniteOfInstrument", UniteOfInstrumentENUM.values());
        return "stock/spec_cloth-update";
    }

    @PostMapping("/general/s_cloth/s_clothUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_UPDATE')")
    public String updateSpecialCloth(@Valid SpecialCloth specialCloth,
                                     @NotNull BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialClothStatus", SpecialClothStatusENUM.values());
            model.addAttribute("uniteOfInstrument", UniteOfInstrumentENUM.values());
            return "stock/spec_cloth-update";
        } else {
            List<Integer> idList = specialClothEmployeeService
                    .findAllEmployeeBySpecialClothEmployeeId(specialCloth.getSpecClothId());
            Employee employee;
            List<Employee> employeeList = new ArrayList<>();
            for (Integer integer : idList) {
                employee = employeeService.findEmployeeById(integer);
                employeeList.add(employee);
            }
            model.addAttribute("employeeList", employeeList);
            if(!employeeList.isEmpty()
                    && specialCloth.getSpecialClothStatusENUM().getTitle().equals("Закрыт")){
                model.addAttribute("error", new StringBuilder(DELETE_OR_CHANGE_STATUS_SCLOTH_MESSAGE));
                return "error/s_cloth-error";
            }
            try{
                List<Employee> currentEmployeesInV = new ArrayList<>(employeeService.findAllEmployees().stream()
                        .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("В отпуске")).toList());
                model.addAttribute("uniteOfInstrument", UniteOfInstrumentENUM.values());
                model.addAttribute("currentEmployeesInV", currentEmployeesInV);
                model.addAttribute("specialClothStatus", SpecialClothStatusENUM.values());
                specialClothService.saveSpecialCloth(specialCloth);
                return "redirect:/general/s_cloth/s_clothView/%d".formatted(specialCloth.getSpecClothId());
            } catch (Exception e){
                model.addAttribute("errorName", new StringBuilder(UNIQUE_SCLOTH_NAME));
                return "stock/spec_cloth-update";
            }
        }
    }
}