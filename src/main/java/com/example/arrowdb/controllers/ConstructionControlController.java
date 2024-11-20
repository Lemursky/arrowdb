package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.ConstructionControlStatusENUM;
import com.example.arrowdb.repositories.RoleRepository;
import com.example.arrowdb.repositories.UsersRepository;
import com.example.arrowdb.services.ConstructionControlService;
import com.example.arrowdb.services.EmployeeService;
import com.example.arrowdb.services.WorkObjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
public class ConstructionControlController {

    private final EmployeeService employeeService;
    private final WorkObjectService workObjectService;
    private final ConstructionControlService constructionControlService;
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/general/constr_control")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_VIEW')")
    public String findAllConstructionControl(Model model) {
        List<WorkObject> workObjectList = workObjectService.findAllWorkObjects()
                .stream()
                .filter(e -> !e.getWorkObjectStatusENUM().getTitle().contains("Не начат"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList();
        model.addAttribute("workObjectList", workObjectList);
        return "constr_control/constr_control-menu";
    }

    @GetMapping("/general/constr_control/constr_controlWarnings/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_VIEW')")
    public String findAllWarningsControl(@PathVariable("id") int id,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         Model model) {
        WorkObject workObject = workObjectService.findWorkObjectById(id);
        Users users = usersRepository.findByUserName(userDetails.getUsername()).orElseThrow();
        model.addAttribute("constructionControlStatus", ConstructionControlStatusENUM.DRAFT);
        model.addAttribute("adminAccept", users.getRolesSet().contains(roleRepository
                .findRolesByRoleName("ROLE_ADMIN")));
        model.addAttribute("roleDraft", users.getRolesSet().contains(roleRepository
                .findRolesByRoleName("ROLE_CONSTR_CONTROL_DRAFT")));
        model.addAttribute("workObject", workObject);
        if(users.getRolesSet().contains(roleRepository.findRolesByRoleName("ROLE_CONSTR_CONTROL_DRAFT")) ||
        users.getRolesSet().contains(roleRepository.findRolesByRoleName("ROLE_ADMIN"))){
            model.addAttribute("constructionControlList", workObject.getConstructionControlList()
                    .stream()
                    .sorted(Comparator.comparingInt(ConstructionControl::getConstrControlId))
                    .toList());
        } else {
            model.addAttribute("constructionControlList", workObject.getConstructionControlList()
                    .stream()
                    .filter(e -> !e.getConstructionControlStatusENUM().getTitle().contains("Черновик"))
                    .sorted(Comparator.comparingInt(ConstructionControl::getConstrControlId))
                    .toList());
        }
        return "constr_control/constr_control-warnings";
    }

    @GetMapping("/general/constr_control/constr_controlCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_CREATE')")
    public String createConstructionControlForm(@ModelAttribute ConstructionControl constructionControl,
                                                Model model) {
        model.addAttribute("employeeList", employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList());
        model.addAttribute("workObjectsList", workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList());
        model.addAttribute("warningStatusList", Arrays.stream(ConstructionControlStatusENUM.values())
                .filter(e -> !e.getTitle().contains("Закрыт"))
                .toList());
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
        List<WorkObject> workObjectsList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList());
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("workObjectsList", workObjectsList);
            model.addAttribute("warningStatusList", ConstructionControlStatusENUM.values());
            return "constr_control/constr_control-create";
        } else {
            try {
                constructionControlService.saveConstructionControl(constructionControl);
                return "redirect:/general/constr_control";
            } catch (Exception e) {
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("workObjectsList", workObjectsList);
                model.addAttribute("warningStatusList", ConstructionControlStatusENUM.values());
                model.addAttribute("errorUniq", UNIQUE_CONST_CONTROL);
                return "constr_control/constr_control-create";
            }
        }
    }

    @GetMapping("/general/constr_control/constr_controlDelete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_DRAFT')")
    public String deleteConstructionControl(@PathVariable("id") int id){
        ConstructionControl constructionControl = constructionControlService.findConstructionControlById(id);
        constructionControlService.deleteConstructionControlById(id);
        return "redirect:/general/constr_control/constr_controlWarnings/%d"
                .formatted(constructionControl.getWorkObject().getWorkObjectId());
    }

    @GetMapping("/general/constr_control/constr_controlUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONSTR_CONTROL_UPDATE')")
    public String updateConstructionControlForm(@PathVariable("id") int id,
                                                Model model) {
        ConstructionControl constructionControl = constructionControlService.findConstructionControlById(id);
        if (constructionControl.getConstructionControlStatusENUM().getTitle().equals("Закрыт")) {
            return "redirect:/general/constr_control/constr_controlWarnings/%d"
                    .formatted(constructionControl.getWorkObject().getWorkObjectId());
        }
        model.addAttribute("constructionControl", constructionControl);
        model.addAttribute("employeeList", new ArrayList<>(employeeService.findAllEmployees()
                .stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList()));
        model.addAttribute("workObjectsList", new ArrayList<>(workObjectService.findAllWorkObjects()
                .stream()
                .filter(e -> e.getWorkObjectStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList()));
        if(constructionControl.getConstructionControlStatusENUM().getTitle().contains("Действующий")){
            model.addAttribute("warningStatusList", Arrays.stream(ConstructionControlStatusENUM
                            .values()).filter(e -> !e.getTitle().contains("Черновик")));
        }
        else if(constructionControl.getConstructionControlStatusENUM().getTitle().contains("Черновик")){
            model.addAttribute("warningStatusList", Arrays.stream(ConstructionControlStatusENUM
                            .values()).filter(e -> !e.getTitle().contains("Закрыт")));
        } else {
            model.addAttribute("warningStatusList", ConstructionControlStatusENUM.values());
        }
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
        List<WorkObject> workObjectsList = new ArrayList<>(workObjectService.findAllWorkObjects().stream()
                .filter(e -> e.getWorkObjectStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(WorkObject::getWorkObjectId))
                .toList());
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("workObjectsList", workObjectsList);
            if(constructionControl.getConstructionControlStatusENUM().getTitle().contains("Действующий")){
                model.addAttribute("warningStatusList", Arrays.stream(ConstructionControlStatusENUM
                                .values()).filter(e -> !e.getTitle().contains("Черновик")));
            }
            else if(constructionControl.getConstructionControlStatusENUM().getTitle().contains("Черновик")){
                model.addAttribute("warningStatusList", Arrays.stream(ConstructionControlStatusENUM
                                .values()).filter(e -> !e.getTitle().contains("Закрыт")));
            } else {
                model.addAttribute("warningStatusList", ConstructionControlStatusENUM.values());
            }
            return "constr_control/constr_control-update";
        } else {
            if (constructionControl.getConstructionControlStatusENUM().getTitle().contains("Закрыт")) {
                constructionControl.setResponsibleFromCustomer(null);
                constructionControl.setResponsibleFromContractor(null);
                constructionControl.setResponsibleFromSKContractor(null);
                constructionControl.setResponsibleFromSubContractor(null);
            }
            try {
                constructionControlService.saveConstructionControl(constructionControl);
                return "redirect:/general/constr_control/constr_controlWarnings/%d"
                        .formatted(constructionControl.getWorkObject().getWorkObjectId());
            } catch (Exception e) {
                model.addAttribute("employeeList", employeeList);
                model.addAttribute("workObjectsList", workObjectsList);
                if(constructionControl.getConstructionControlStatusENUM().getTitle().contains("Действующий")){
                    model.addAttribute("warningStatusList", Arrays.stream(ConstructionControlStatusENUM
                                    .values()).filter(p -> !p.getTitle().contains("Черновик")));
                }
                else if(constructionControl.getConstructionControlStatusENUM().getTitle().contains("Черновик")){
                    model.addAttribute("warningStatusList", Arrays.stream(ConstructionControlStatusENUM
                                    .values()).filter(p -> p.getTitle().contains("Закрыт")));
                } else {
                    model.addAttribute("warningStatusList", ConstructionControlStatusENUM.values());
                }
                model.addAttribute("errorUniq", UNIQUE_CONST_CONTROL);
                return "constr_control/constr_control-update";
            }
        }
    }
}