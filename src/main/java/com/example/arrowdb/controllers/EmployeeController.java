package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.EmployeeStatusENUM;
import com.example.arrowdb.repositories.RoleRepository;
import com.example.arrowdb.repositories.UsersRepository;
import com.example.arrowdb.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ProfessionService professionService;
    private final DriverLicenseService driverLicenseService;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/general/employee")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_VIEW')")
    public String getEmployeesList(Model model) {
        model.addAttribute("employeeList", employeeService.findAllEmployees().stream()
                .sorted(Comparator.comparingInt((Employee::getEmpId)))
                .toList());
        return "employee/employee-menu";
    }

    @GetMapping("/general/employee/employeeView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_VIEW')")
    public String findEmployeeById(@PathVariable("id") int id,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model) {
        Users users = usersRepository.findByUserName(userDetails.getUsername()).orElseThrow();
        model.addAttribute("employee", employeeService.findEmployeeById(id));
        model.addAttribute("userName", userDetails.getUsername());
        model.addAttribute("adminAccept", users.getRolesSet().contains(roleRepository
                .findRolesByRoleName("ROLE_ADMIN")));
        model.addAttribute("personalAccept", users.getRolesSet().contains(roleRepository
                .findRolesByRoleName("ROLE_EMPLOYEE_PERSONAL")));
        model.addAttribute("financeAccept", users.getRolesSet().contains(roleRepository
                .findRolesByRoleName("ROLE_EMPLOYEE_FINANCE")));
        return "employee/employee-view";
    }

    @GetMapping("/general/employee/employeeCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_CREATE')")
    public String createEmployeeForm(@ModelAttribute Employee employee,
                                     Model model) {
        model.addAttribute("professionList", professionService.findAllProfessions());
        return "employee/employee-create";
    }

    @PostMapping("/general/employee/employeeCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_CREATE')")
    public String createEmployee(@Valid Employee employee,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("professionList", professionService.findAllProfessions());
            return "employee/employee-create";
        } else {
            employee.setEmployeeStatusENUM(EmployeeStatusENUM.ACTIVE);
            employeeService.saveEmployee(employee);
            return "redirect:/general/employee";
        }
    }

    @GetMapping("/general/employee/employeeDelete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_DELETE')")
    public String deleteEmployee(@PathVariable("id") int id, Model model) {
        try {
            employeeService.deleteEmployeeById(id);
        } catch (Exception e) {
            model.addAttribute("employee", employeeService.findEmployeeById(id));
            model.addAttribute("error", DELETE_OR_CHANGE_STATUS_EMPLOYEE_MESSAGE);
            return "error/employee-error";
        }
        return "redirect:/general/employee";
    }

    @GetMapping("/general/employee/employeeUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_UPDATE')")
    public String updateEmployeeForm(@PathVariable("id") int id,
                                     Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        if(employee.getEmployeeStatusENUM().getTitle().equals(EmployeeStatusENUM.CLOTHED.getTitle())){
            return "redirect:/general/employee/employeeView/%d".formatted(employee.getEmpId());
        }
        List<Profession> professionList = professionService.findAllProfessions();
        List<DriverLicense> driverLicenseList = new ArrayList<>(driverLicenseService.findAllDriverLicense().stream()
                .sorted(Comparator.comparingInt((DriverLicense::getDrLiId)))
                .toList());
        model.addAttribute("employee", employee);
        model.addAttribute("professionList", professionList);
        model.addAttribute("driverLicenseList", driverLicenseList);
        model.addAttribute("employeeStatus", EmployeeStatusENUM.values());
        return "employee/employee-update";
    }

    @PostMapping("/general/employee/employeeUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_UPDATE')")
    public String updateEmployee(@Valid @ModelAttribute Employee employee,
                                 BindingResult bindingResult,
                                 Model model) {
        List<Profession> professionList = professionService.findAllProfessions();
        List<DriverLicense> driverLicenseList = new ArrayList<>(driverLicenseService.findAllDriverLicense().stream()
                .sorted(Comparator.comparingInt((DriverLicense::getDrLiId)))
                .toList());
        if (bindingResult.hasErrors()) {
            model.addAttribute("professionList", professionList);
            model.addAttribute("driverLicenseList", driverLicenseList);
            model.addAttribute("employeeStatus", EmployeeStatusENUM.values());
            return "employee/employee-update";
        } else {
            Employee empById = employeeService.findEmployeeById(employee.getEmpId());
            if (!empById.getPersonalInstrumentList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                    .equals("Закрыт") ||
                    !empById.getWorkInstrumentList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                            .equals("Закрыт") ||
                    !empById.getMeasInstrumentList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                            .equals("Закрыт") ||
                    !empById.getSpecialClothEmployeeList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                            .equals("Закрыт") ||
                    !empById.getWorkObjectChiefList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                            .equals("Закрыт") ||
                    !empById.getWorkObjectPTOList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                            .equals("Закрыт") ||
                    !empById.getWorkObjectSupplierList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                            .equals("Закрыт") ||
                    !empById.getWorkObjectStoreKeeperList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                            .equals("Закрыт") ||
                    !empById.getConstrControlEmpDutyList().isEmpty() && employee.getEmployeeStatusENUM().getTitle()
                            .equals("Закрыт")) {
                model.addAttribute("employee", empById);
                model.addAttribute("error", DELETE_OR_CHANGE_STATUS_EMPLOYEE_MESSAGE);
                return "error/employee-error";
            } else {
                employeeService.saveEmployee(employee);
            }
        }
        return "redirect:/general/employee/employeeView/%d".formatted(employee.getEmpId());
    }
}