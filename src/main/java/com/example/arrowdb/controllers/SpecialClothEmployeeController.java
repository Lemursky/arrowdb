package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

import static com.example.arrowdb.message.Message.SPEC_CLOTH_UNIQUE_ERROR;

@Controller
@RequiredArgsConstructor
public class SpecialClothEmployeeController {

    private final SpecialClothEmployeeService specialClothEmployeeService;
    private final EmployeeService employeeService;
    private final SpecialClothService specialClothService;

    @GetMapping("/general/s_cloth/s_cloth-emp_update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_UPDATE')")
    public String updateSpecialClothEmployee(@PathVariable("id") int id,
                                             @ModelAttribute SpecialClothEmployee specialClothEmployee,
                                             Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        List<SpecialClothEmployee> specialClothEmployeeList = specialClothEmployeeService
                .findAllSpecialClothEmployeeByEmployeeId(id);
        List<SpecialCloth> specialClothList = specialClothService.findAllSpecialCloths().stream()
                .sorted(Comparator.comparingInt((SpecialCloth::getSpecClothId)))
                .filter(e -> e.getSpecClothStatus().getStatusName().equals("Действующий"))
                .toList();
        model.addAttribute("specialClothEmployeeList", specialClothEmployeeList);
        model.addAttribute("employee", employee);
        model.addAttribute("specialClothList", specialClothList);
        return "stock/spec_cloth-emp_update";
    }

    @GetMapping("/general/s_cloth/s_clothDelete_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_UPDATE')")
    public String deleteSpecialClothEmployee(@PathVariable("id") int id) {
        Integer localId = specialClothEmployeeService.findEmployeeIdBySpecialClothEmployeeId(id);
        specialClothEmployeeService.deleteSpecialClothEmployeeById(id);
        return "redirect:/general/s_cloth/s_cloth-emp_update/%d".formatted(localId);
    }

    @PostMapping("/general/s_cloth/s_clothCreate_employee/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE_SCLOTH_UPDATE')")
    public String createSpecialClothEmployeeForm(@PathVariable("id") int id,
                                                 @ModelAttribute SpecialClothEmployee specialClothEmployee,
                                                 Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        List<SpecialClothEmployee> specialClothEmployeeList = specialClothEmployeeService
                .findAllSpecialClothEmployeeByEmployeeId(id);
        List<SpecialCloth> specialClothList = specialClothService.findAllSpecialCloths().stream()
                .sorted(Comparator.comparingInt((SpecialCloth::getSpecClothId)))
                .filter(e -> e.getSpecClothStatus().getStatusName().equals("Действующий"))
                .toList();
        List<Integer> specialClothListByEmpId = specialClothEmployeeService.findSpecialClothByEmployeeId(id);
        specialClothEmployee.setEmpId(id);
        specialClothEmployee.setEmployee(employee);
        model.addAttribute("employee", employee);
        if (specialClothListByEmpId.contains(specialClothEmployee.getSpecialCloth().getSpecClothId())) {
            model.addAttribute("specialClothEmployeeList", specialClothEmployeeList);
            model.addAttribute("specialClothList", specialClothList);
            model.addAttribute("error", SPEC_CLOTH_UNIQUE_ERROR);
            return "stock/spec_cloth-emp_update";
        }
            specialClothEmployeeService.saveSpecialClothEmployee(specialClothEmployee);
            return "redirect:/general/s_cloth/s_cloth-emp_update/%d".formatted(id);

    }
}