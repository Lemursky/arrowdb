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

import java.util.*;

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ProfessionService professionService;
    private final DriverLicenseService driverLicenseService;
    private final EmployeeStatusService employeeStatusService;

    @GetMapping("/general/employee")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_VIEW')")
    public String getEmployeesList(Model model) {
        List<Employee> employee = employeeService.findAllEmployees().stream()
                .sorted(Comparator.comparingInt((Employee::getEmpId)))
                .toList();
        model.addAttribute("employee", employee);
        return "employee/employee-menu";
    }

    @GetMapping("/general/employee/employeeView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_VIEW')")
    public String findEmployeeById(@PathVariable("id") int id,
                                   Model model) {
        Employee employee = employeeService.findEmployeeById(id);
        List<SpecialClothEmployee> specialClothEmployeeList = employee.getSpecialClothEmployeeList();
        model.addAttribute("specialClothEmployeeList", specialClothEmployeeList);
        model.addAttribute("employee", employee);
        return "employee/employee-view";
    }

    @GetMapping("/general/employee/employeeCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_CREATE')")
    public String createEmployeeForm(@ModelAttribute Employee employee,
                                     Model model) {
        List<Profession> professionList = professionService.findAllProfessions();
        model.addAttribute("professionList", professionList);
        return "employee/employee-create";
    }

    @PostMapping("/general/employee/employeeCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_CREATE')")
    public String createEmployee(@Valid Employee employee,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            List<Profession> professionList = professionService.findAllProfessions();
            model.addAttribute("professionList", professionList);
            return "employee/employee-create";
        } else {
            employee.setEmpStatus(employeeStatusService.findEmployeeStatusByStatusName("Действующий"));
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
            Employee employee = employeeService.findEmployeeById(id);
            model.addAttribute("employee", employee);
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
        if(employee.getEmpStatus().getStatusName().equals("Закрыт")){
            return "redirect:/general/employee/employeeView/%d".formatted(employee.getEmpId());
        }
        List<Profession> professionList = professionService.findAllProfessions();
        List<DriverLicense> driverLicenseList = new ArrayList<>(driverLicenseService.findAllDriverLicense().stream()
                .sorted(Comparator.comparingInt((DriverLicense::getDrLiId)))
                .toList());
        List<EmployeeStatus> employeeStatus = employeeStatusService.findAllEmployeeStatus();
        employeeStatus.remove(employeeStatusService.findEmployeeStatusByStatusName("В отпуске"));
        model.addAttribute("employee", employee);
        model.addAttribute("professionList", professionList);
        model.addAttribute("driverLicenseList", driverLicenseList);
        model.addAttribute("employeeStatus", employeeStatus);
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
        List<EmployeeStatus> employeeStatus = employeeStatusService.findAllEmployeeStatus();
        employeeStatus.remove(employeeStatusService.findEmployeeStatusByStatusName("В отпуске"));
        if (bindingResult.hasErrors()) {
            model.addAttribute("professionList", professionList);
            model.addAttribute("driverLicenseList", driverLicenseList);
            model.addAttribute("employeeStatus", employeeStatus);
            return "employee/employee-update";
        } else {
            Employee empById = employeeService.findEmployeeById(employee.getEmpId());
            if (!empById.getPersonalInstrumentList().isEmpty() && employee.getEmpStatus().getStatusName()
                    .equals("Закрыт") ||
                    !empById.getWorkInstrumentList().isEmpty() && employee.getEmpStatus().getStatusName()
                            .equals("Закрыт") ||
                    !empById.getMeasInstrumentList().isEmpty() && employee.getEmpStatus().getStatusName()
                            .equals("Закрыт") ||
                    !empById.getSpecialClothEmployeeList().isEmpty() && employee.getEmpStatus().getStatusName()
                            .equals("Закрыт") ||
                    !empById.getWorkObjectChiefList().isEmpty() && employee.getEmpStatus().getStatusName()
                            .equals("Закрыт") ||
                    !empById.getWorkObjectPTOList().isEmpty() && employee.getEmpStatus().getStatusName()
                            .equals("Закрыт") ||
                    !empById.getWorkObjectSupplierList().isEmpty() && employee.getEmpStatus().getStatusName()
                            .equals("Закрыт") ||
                    !empById.getWorkObjectStoreKeeperList().isEmpty() && employee.getEmpStatus().getStatusName()
                            .equals("Закрыт") ||
                    !empById.getConstrControlEmpDutyList().isEmpty() && employee.getEmpStatus().getStatusName()
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