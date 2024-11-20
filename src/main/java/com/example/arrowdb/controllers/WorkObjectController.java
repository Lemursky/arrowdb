package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.WorkObjectStatusENUM;
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

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class WorkObjectController {

    private final WorkObjectService workObjectService;
    private final EmployeeService employeeService;
    private final WorkInstrumentService workInstrumentService;
    private final MeasInstrumentService measInstrumentService;

    @GetMapping("/general/workobject")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_VIEW')")
    public String getWorkObjectList(Model model) {
        model.addAttribute("workObjects", workObjectService.findAllWorkObjects().stream()
                .sorted(Comparator.comparingInt((WorkObject::getWorkObjectId)))
                .toList());
        return "work_object/work_object-menu";
    }

    @GetMapping("/general/workobject/workobjectView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_VIEW')")
    public String findWorkObjectById(@PathVariable("id") int id,
                                     Model model) {;
        model.addAttribute("workObject", workObjectService.findWorkObjectById(id));
        return "work_object/work_object-view";
    }

    @GetMapping("/general/workobject/workobjectCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_CREATE')")
    public String createWorkObjectForm(@ModelAttribute WorkObject workObject) {
        workObject.setWorkObjectStatusENUM(WorkObjectStatusENUM.NOT_STARTED);
        return "work_object/work_object-create";
    }

    @PostMapping("/general/workobject/workobjectCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_CREATE')")
    public String createWorkObject(@Valid WorkObject workObject,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "work_object/work_object-create";
        } else {
            try {
                workObject.setWorkObjectStatusENUM(WorkObjectStatusENUM.NOT_STARTED);
                workObjectService.saveWorkObject(workObject);
                return "redirect:/general/workobject";
            } catch (Exception e) {
                model.addAttribute("errorName", new StringBuilder(UNIQUE_WORK_OBJECT));
                return "work_object/work_object-create";
            }
        }
    }

    @GetMapping("/general/workobject/workobjectDelete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_DELETE')")
    public String deleteWorkObject(@PathVariable("id") int id,
                                   Model model) {
        try {
            workObjectService.deleteWorkObjectById(id);
        } catch (Exception e) {
            WorkObject workObject = workObjectService.findWorkObjectById(id);
            model.addAttribute("workObject", workObject);
            model.addAttribute("error", DELETE_OR_CHANGE_STATUS_WORK_OBJECT_MESSAGE);
            return "error/work_object-error";
        }
        return "redirect:/general/workobject";
    }

    @GetMapping("/general/workobject/workobjectUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_UPDATE')")
    public String updateWorkObjectForm(@PathVariable("id") int id,
                                       Model model) {
        WorkObject workObject = workObjectService.findWorkObjectById(id);
        if(workObject.getWorkObjectStatusENUM().getTitle().contains("Закрыт")){
            return "redirect:/general/workobject/workobjectView/%d".formatted(workObject.getWorkObjectId());
        }
        model.addAttribute("workObject", workObject);
        model.addAttribute("workObjectStatusList", Arrays.stream(WorkObjectStatusENUM.values())
                .filter(e -> !e.getTitle().contains("Не начат"))
                .toList());
        model.addAttribute("employeeList", new ArrayList<>(employeeService.findAllEmployees()
                .stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий")).toList()));
        model.addAttribute("employeeForChief", new ArrayList<>(employeeService.findAllEmployees()
                .stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("Руководитель"))
                .toList()));
        model.addAttribute("employeeForSupplier", new ArrayList<>(employeeService.findAllEmployees()
                .stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("МТР"))
                .toList()));
        model.addAttribute("workObjectChief", workObject.getWorkObjectChief());
        model.addAttribute("storeKeeperList", workObject.getStoreKeeperList());
        model.addAttribute("employeeForStoreKeeper", new ArrayList<>(employeeService.findAllEmployees()
                .stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("Кладовщик"))
                .toList()));
        model.addAttribute("supplierList", workObject.getSupplierList());
        model.addAttribute("workInstrumentList", workInstrumentService.findAllWorkInstruments());
        model.addAttribute("measInstrumentList", measInstrumentService.findAllMeasInstruments());
        model.addAttribute("employeeForPTO", new ArrayList<>(employeeService.findAllEmployees()
                .stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("ПТО"))
                .toList()));
        model.addAttribute("PTOList", workObject.getPTOList());
        return "work_object/work_object-update";
    }

    @PostMapping("/general/workobject/workobjectUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_UPDATE')")
    public String updateWorkObject(@Valid WorkObject workObject,
                                   @NotNull BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("workObjectStatusList", Arrays.stream(WorkObjectStatusENUM.values())
                    .filter(e -> !e.getTitle().contains("Не начат"))
                    .toList());
            model.addAttribute("employeeList", new ArrayList<>(employeeService.findAllEmployees()
                    .stream()
                    .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                    .toList()));
            model.addAttribute("employeeForChief", new ArrayList<>(employeeService.findAllEmployees()
                    .stream()
                    .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                    .filter(e -> e.getProfession().getProfessionName().contains("Руководитель"))
                    .toList()));
            model.addAttribute("employeeForSupplier", new ArrayList<>(employeeService.findAllEmployees()
                    .stream()
                    .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                    .filter(e -> e.getProfession().getProfessionName().contains("МТР"))
                    .toList()));
            model.addAttribute("workObjectChief", workObject.getWorkObjectChief());
            model.addAttribute("storeKeeperList", workObject.getStoreKeeperList());
            model.addAttribute("employeeForStoreKeeper", new ArrayList<>(employeeService.findAllEmployees()
                    .stream()
                    .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                    .filter(e -> e.getProfession().getProfessionName().contains("Кладовщик"))
                    .toList()));
            model.addAttribute("supplierList", workObject.getSupplierList());
            model.addAttribute("employeeForPTO", new ArrayList<>(employeeService.findAllEmployees()
                    .stream()
                    .filter(e -> e.getEmployeeStatusENUM().getTitle().equals("Действующий"))
                    .filter(e -> e.getProfession().getProfessionName().contains("ПТО"))
                    .toList()));
            model.addAttribute("PTOList", workObject.getPTOList());
            return "work_object/work_object-update";
        } else {
            WorkObject workObjectById = workObjectService.findWorkObjectById(workObject.getWorkObjectId());
            if (!workObjectById.getWorkInstrumentList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                    .contains("Закрыт") ||
                    !workObjectById.getWorkInstrumentList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Приостановлен") ||
                    !workObjectById.getMeasInstrumentList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Закрыт") ||
                    !workObjectById.getMeasInstrumentList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Приостановлен") ||
                    workObjectById.getWorkObjectChief() != null && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Закрыт") ||
                    workObjectById.getWorkObjectChief() != null && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Приостановлен") ||
                    !workObjectById.getPTOList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Закрыт") ||
                    !workObjectById.getPTOList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Приостановлен") ||
                    !workObjectById.getStoreKeeperList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Закрыт") ||
                    !workObjectById.getStoreKeeperList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Приостановлен") ||
                    !workObjectById.getSupplierList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Закрыт") ||
                    !workObjectById.getSupplierList().isEmpty() && workObject.getWorkObjectStatusENUM().getTitle()
                            .contains("Приостановлен") ||
                    (workObjectById.getConstructionControlActive() != 0) &&
                            workObject.getWorkObjectStatusENUM().getTitle().contains("Закрыт") ||
                    (workObjectById.getConstructionControlActive() != 0) &&
                            workObject.getWorkObjectStatusENUM().getTitle().contains("Приостановлен")
            ) {
                model.addAttribute("workObject", workObjectById);
                model.addAttribute("constructionControlListActive", workObjectById
                        .getConstructionControlList()
                        .stream()
                        .filter(e -> e.getConstructionControlStatusENUM().getTitle().contains("Действующий"))
                        .toList());
                model.addAttribute("error", DELETE_OR_CHANGE_STATUS_WORK_OBJECT_MESSAGE);
                return "error/work_object-error";
            } else {
                workObjectService.saveWorkObject(workObject);
            }
        }
        return "redirect:/general/workobject/workobjectView/%d".formatted(workObject.getWorkObjectId());
    }
}