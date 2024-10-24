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
    private final PersonalInstrumentService personalInstrumentService;
    private final WorkInstrumentService workInstrumentService;
    private final MeasInstrumentService measInstrumentService;
    private final SpecialClothEmployeeService specialClothEmployeeService;

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
            model.addAttribute("error", new StringBuilder(DELETE_OR_CHANGE_STATUS_EMPLOYEE_MESSAGE));
            return "error/employee-error";
        }
        return "redirect:/general/employee";
    }

    @GetMapping("/general/employee/employeeUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE_UPDATE')")
    public String updateEmployeeForm(@PathVariable("id") int id,
                                     Model model) {
        Employee employee = employeeService.findEmployeeById(id);
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
    public String updateEmployee(@Valid Employee employee,
                                 BindingResult bindingResult,
                                 Model model) {
        List<Profession> professionList = professionService.findAllProfessions();
        List<DriverLicense> driverLicenseList = new ArrayList<>(driverLicenseService.findAllDriverLicense().stream()
                .sorted(Comparator.comparingInt((DriverLicense::getDrLiId)))
                .toList());
        List<EmployeeStatus> employeeStatus = employeeStatusService.findAllEmployeeStatus();
        employeeStatus.remove(employeeStatusService.findEmployeeStatusByStatusName("В отпуске"));
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            model.addAttribute("professionList", professionList);
            model.addAttribute("driverLicenseList", driverLicenseList);
            model.addAttribute("employeeStatus", employeeStatus);
            return "employee/employee-update";
        } else {
            if (employee.getEmpStatus().getStatusName().equals("Закрыт") && employee.getCloseDate() == null){
                model.addAttribute("employee", employee);
                model.addAttribute("professionList", professionList);
                model.addAttribute("driverLicenseList", driverLicenseList);
                model.addAttribute("employeeStatus", employeeStatus);
                model.addAttribute("errorStatus", new StringBuilder(MESSAGE_CHANGE_STATUS_DATE));
                return "employee/employee-update";
            }
            List<Integer> personalIntegerList = personalInstrumentService.findAllIdEmployees(employee.getEmpId());
            List<Integer> workInstrumentList = workInstrumentService.findAllIdEmployees(employee.getEmpId());
            List<Integer> measInstrumentList = measInstrumentService.findAllIdEmployees(employee.getEmpId());
            List<Integer> specialClothList = specialClothEmployeeService.findAllEmployeeBySpecialClothEmployeeId(employee.getEmpId());
            if (personalIntegerList.contains(employee.getEmpId()) && employee.getEmpStatus().getStatusName().equals("Закрыт") ||
                    workInstrumentList.contains(employee.getEmpId()) && employee.getEmpStatus().getStatusName().equals("Закрыт") ||
                    measInstrumentList.contains(employee.getEmpId()) && employee.getEmpStatus().getStatusName().equals("Закрыт") ||
                    specialClothList.contains(employee.getEmpId()) && employee.getEmpStatus().getStatusName().equals("Закрыт")) {
                Employee employees = employeeService.findEmployeeById(employee.getEmpId());
                model.addAttribute("employee", employees);
                model.addAttribute("error", new StringBuilder(DELETE_OR_CHANGE_STATUS_EMPLOYEE_MESSAGE));
                return "error/employee-error";
            } else {
                employeeService.saveEmployee(employee);
            }
        }
        return "redirect:/general/employee/employeeView/%d".formatted(employee.getEmpId());
    }
}