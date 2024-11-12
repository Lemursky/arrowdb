package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.services.ConstructionControlService;
import com.example.arrowdb.services.EmployeeService;
import com.example.arrowdb.services.WorkObjectService;
import com.example.arrowdb.services.WorkObjectStatusService;
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
public class ConstructionControlController {

    private final EmployeeService employeeService;
    private final WorkObjectService workObjectService;
    private final WorkObjectStatusService workObjectStatusService;
    private final ConstructionControlService constructionControlService;

    @GetMapping("/general/constr_control")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_VIEW')")
    public String findAllConstructionControl(Model model) {
        List<WorkObject> workObjectList = workObjectService.findWorkObjectByStatus(
                workObjectStatusService.findWorkObjectStatusIdByStatusName("Действующий"),
                workObjectStatusService.findWorkObjectStatusIdByStatusName("Приостановлен"),
                workObjectStatusService.findWorkObjectStatusIdByStatusName("Закрыт"));
        model.addAttribute("workObjectList", workObjectList);
        return "constr_control/constr_control-menu";
    }

    @GetMapping("/general/constr_control/constr_controlWarnings/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_VIEW')")
    public String findAllWarningsControl(@PathVariable("id") int id,
                                         Model model) {
        WorkObject workObject = workObjectService.findWorkObjectById(id);
        List<ConstructionControl> constructionControlList = workObject.getConstructionControlList().stream()
                .sorted(Comparator.comparingInt(ConstructionControl::getConstrControlId))
                .toList();
        model.addAttribute("workObject", workObject);
        model.addAttribute("constructionControlList", constructionControlList);
        return "constr_control/constr_control-warnings";
    }

    @GetMapping("/general/constr_control/constr_controlCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_CREATE')")
    public String createConstructionControlForm(@ModelAttribute ConstructionControl constructionControl,
                                                Model model) {
        List<Employee> employeeList = employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList();
        List<WorkObjectStatus> warningStatusList = new ArrayList<>(workObjectStatusService
                .findAllWorkObjectStatus().stream()
                .sorted(Comparator.comparingInt(WorkObjectStatus::getWObjStatId))
                .toList());
        warningStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Не начат"));
        warningStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Приостановлен"));
        List<WorkObject> workObjectsList = workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStat().getStatusName().equals("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList();
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("workObjectsList", workObjectsList);
        model.addAttribute("warningStatusList", warningStatusList);
        return "constr_control/constr_control-create";
    }

    @PostMapping("/general/constr_control/constr_controlCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_CREATE')")
    public String creatConstructionControl(@Valid ConstructionControl constructionControl,
                                           BindingResult bindingResult,
                                           Model model) {
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList());
        List<WorkObjectStatus> warningStatusList = new ArrayList<>(workObjectStatusService
                .findAllWorkObjectStatus().stream()
                .sorted(Comparator.comparingInt(WorkObjectStatus::getWObjStatId))
                .toList());
        warningStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Не начат"));
        warningStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Приостановлен"));
        List<WorkObject> workObjectsList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStat().getStatusName().equals("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList());
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("workObjectsList", workObjectsList);
            model.addAttribute("warningStatusList", warningStatusList);
            return "constr_control/constr_control-create";
        } else {
            try {
                constructionControlService.saveConstructionControl(constructionControl);
                return "redirect:/general/constr_control";
            } catch (Exception e) {
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("workObjectsList", workObjectsList);
                model.addAttribute("warningStatusList", warningStatusList);
                model.addAttribute("errorUniq", UNIQUE_CONST_CONTROL);
                return "constr_control/constr_control-create";
            }
        }
    }

    @GetMapping("/general/constr_control/constr_controlUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_UPDATE')")
    public String updateConstructionControlForm(@PathVariable("id") int id,
                                                Model model) {
        ConstructionControl constructionControl = constructionControlService.findConstructionControlById(id);
        if (constructionControl.getWarningStatus().getStatusName().equals("Закрыт")) {
            return "redirect:/general/constr_control/constr_controlWarnings/%d"
                    .formatted(constructionControl.getWorkObject().getWorkObjectId());
        }
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList());
        List<WorkObjectStatus> warningStatusList = new ArrayList<>(workObjectStatusService
                .findAllWorkObjectStatus().stream()
                .sorted(Comparator.comparingInt(WorkObjectStatus::getWObjStatId))
                .toList());
        warningStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Не начат"));
        warningStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Приостановлен"));
        List<WorkObject> workObjectsList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStat().getStatusName().equals("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList());
        model.addAttribute("constructionControl", constructionControl);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("workObjectsList", workObjectsList);
        model.addAttribute("warningStatusList", warningStatusList);
        return "constr_control/constr_control-update";
    }

    @PostMapping("/general/constr_control/constr_controlUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_UPDATE')")
    public String updateConstructionControlForm(@Valid @ModelAttribute ConstructionControl constructionControl,
                                                @NotNull BindingResult bindingResult,
                                                Model model) {
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList());
        List<WorkObjectStatus> warningStatusList = new ArrayList<>(workObjectStatusService
                .findAllWorkObjectStatus().stream()
                .sorted(Comparator.comparingInt(WorkObjectStatus::getWObjStatId))
                .toList());
        warningStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Не начат"));
        warningStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Приостановлен"));
        List<WorkObject> workObjectsList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStat().getStatusName().equals("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList());
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("workObjectsList", workObjectsList);
            model.addAttribute("warningStatusList", warningStatusList);
            return "constr_control/constr_control-update";
        } else {
            if (constructionControl.getWarningStatus().getStatusName().equals("Закрыт")) {
                constructionControl.setResponsibleFromContractor(null);
                constructionControl.setResponsibleFromCustomer(null);
                constructionControl.getEmpDutyList().clear();
            }
            try {
                constructionControlService.saveConstructionControl(constructionControl);
                return "redirect:/general/constr_control/constr_controlWarnings/%d"
                        .formatted(constructionControl.getWorkObject().getWorkObjectId());
            } catch (Exception e) {
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("workObjectsList", workObjectsList);
                model.addAttribute("warningStatusList", warningStatusList);
                model.addAttribute("errorUniq", UNIQUE_CONST_CONTROL);
                return "constr_control/constr_control-update";
            }
        }
    }

}
