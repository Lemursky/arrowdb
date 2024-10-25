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
import java.util.HashSet;
import java.util.List;

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class WorkObjectController {

    private final WorkObjectService workObjectService;
    private final WorkObjectStatusService workObjectStatusService;
    private final EmployeeService employeeService;
    private final WorkInstrumentService workInstrumentService;
    private final MeasInstrumentService measInstrumentService;
    private final ConstructionControlService constructionControlService;

    @GetMapping("/general/workobject")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_VIEW')")
    public String getWorkObjectList(Model model) {
        List<WorkObject> workObjects = workObjectService.findAllWorkObjects().stream()
                .sorted(Comparator.comparingInt((WorkObject::getWorkObjectId)))
                .toList();
        model.addAttribute("workObjects", workObjects);
        return "work_object/work_object-menu";
    }

    @GetMapping("/general/workobject/workobjectView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_VIEW')")
    public String findWorkObjectById(@PathVariable("id") int id,
                                     Model model) {
        WorkObject workObject = workObjectService.findWorkObjectById(id);
        model.addAttribute("workObject", workObject);
        return "work_object/work_object-view";
    }

    @GetMapping("/general/workobject/workobjectCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_CREATE')")
    public String createWorkObjectForm(@ModelAttribute WorkObject workObject) {
        workObject.setWorkObjectStat(workObjectStatusService.findWorkObjectStatusByStatusName("Не начат"));
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
                workObject.setWorkObjectStat(workObjectStatusService.findWorkObjectStatusByStatusName("Не начат"));
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
        List<Employee> employeeList = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий")).toList());
        List<WorkObjectStatus> workObjectStatusList = workObjectStatusService.findAllWorkObjectStatus();
        workObjectStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Не начат"));
        List<WorkInstrument> workInstrumentList = workInstrumentService.findAllWorkInstruments();
        List<MeasInstrument> measInstrumentList = measInstrumentService.findAllMeasInstruments();
        Employee workObjectChief = workObject.getWorkObjectChief();
        List<Employee> storeKeeperList = workObject.getStoreKeeperList();
        List<Employee> supplierList = workObject.getSupplierList();
        List<Employee> PTOList = workObject.getPTOList();
        List<Employee> employeeForStoreKeeper = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("Кладовщик"))
                .toList());
        List<Employee> employeeForSupplier = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("МТР"))
                .toList());
        List<Employee> employeeForPTO = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("ПТО"))
                .toList());
        List<Employee> employeeForChief = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("Руководитель"))
                .toList());
        model.addAttribute("workObject", workObject);
        model.addAttribute("workObjectStatusList", workObjectStatusList);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("employeeForChief", employeeForChief);
        model.addAttribute("employeeForSupplier", employeeForSupplier);
        model.addAttribute("workObjectChief", workObjectChief);
        model.addAttribute("storeKeeperList", storeKeeperList);
        model.addAttribute("employeeForStoreKeeper", employeeForStoreKeeper);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("workInstrumentList", workInstrumentList);
        model.addAttribute("measInstrumentList", measInstrumentList);
        model.addAttribute("employeeForPTO", employeeForPTO);
        model.addAttribute("PTOList", PTOList);
        return "work_object/work_object-update";
    }

    @PostMapping("/general/workobject/workobjectUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_WORK_OBJECT_UPDATE')")
    public String updateWorkObject(@Valid WorkObject workObject,
                                   @NotNull BindingResult bindingResult,
                                   Model model) {
        List<Employee> employeeList = new java.util.ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий")).toList());
        List<WorkObjectStatus> workObjectStatusList = workObjectStatusService.findAllWorkObjectStatus();
        workObjectStatusList.remove(workObjectStatusService.findWorkObjectStatusByStatusName("Не начат"));
        List<WorkInstrument> workInstrumentList = workInstrumentService.findAllWorkInstruments();
        List<MeasInstrument> measInstrumentList = measInstrumentService.findAllMeasInstruments();
        Employee workObjectChief = workObject.getWorkObjectChief();
        List<Employee> storeKeeperList = workObject.getStoreKeeperList();
        List<Employee> supplierList = workObject.getSupplierList();
        List<Employee> PTOList = workObject.getPTOList();
        List<Employee> employeeForStoreKeeper = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("Кладовщик"))
                .toList());
        List<Employee> employeeForSupplier = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("МТР"))
                .toList());
        List<Employee> employeeForPTO = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("ПТО"))
                .toList());
        List<Employee> employeeForChief = new ArrayList<>(employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmpStatus().getStatusName().equals("Действующий"))
                .filter(e -> e.getProfession().getProfessionName().contains("Руководитель"))
                .toList());
        if (bindingResult.hasErrors()) {
            model.addAttribute("workObjectStatusList", workObjectStatusList);
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("employeeForChief", employeeForChief);
            model.addAttribute("employeeForSupplier", employeeForSupplier);
            model.addAttribute("workObjectChief", workObjectChief);
            model.addAttribute("storeKeeperList", storeKeeperList);
            model.addAttribute("employeeForStoreKeeper", employeeForStoreKeeper);
            model.addAttribute("supplierList", supplierList);
            model.addAttribute("workInstrumentList", workInstrumentList);
            model.addAttribute("measInstrumentList", measInstrumentList);
            model.addAttribute("employeeForPTO", employeeForPTO);
            model.addAttribute("PTOList", PTOList);
            return "work_object/work_object-update";
        } else {
            WorkObject workObjectById = workObjectService.findWorkObjectById(workObject.getWorkObjectId());
            List<ConstructionControl> constructionControlListActive = workObjectById.getConstructionControlList().stream()
                    .filter(e -> e.getWarningStatus().getStatusName().equals("Действующий"))
                    .toList();
            if (!workObjectById.getWorkInstrumentList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                    .equals("Закрыт") ||
                    !workObjectById.getWorkInstrumentList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Приостановлен") ||
                    !workObjectById.getMeasInstrumentList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Закрыт") ||
                    !workObjectById.getMeasInstrumentList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Приостановлен") ||
                    workObjectById.getWorkObjectChief() != null && workObject.getWorkObjectStat().getStatusName()
                            .equals("Закрыт") ||
                    workObjectById.getWorkObjectChief() != null && workObject.getWorkObjectStat().getStatusName()
                            .equals("Приостановлен") ||
                    !workObjectById.getPTOList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Закрыт") ||
                    !workObjectById.getPTOList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Приостановлен") ||
                    !workObjectById.getStoreKeeperList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Закрыт") ||
                    !workObjectById.getStoreKeeperList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Приостановлен") ||
                    !workObjectById.getSupplierList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Закрыт") ||
                    !workObjectById.getSupplierList().isEmpty() && workObject.getWorkObjectStat().getStatusName()
                            .equals("Приостановлен") ||
                    (workObjectById.getConstructionControlActive() != 0) &&
                            workObject.getWorkObjectStat().getStatusName().equals("Закрыт") ||
                    (workObjectById.getConstructionControlActive() != 0) &&
                            workObject.getWorkObjectStat().getStatusName().equals("Приостановлен")
            ) {
                model.addAttribute("workObject", workObjectById);
                model.addAttribute("constructionControlListActive", constructionControlListActive);
                model.addAttribute("error", DELETE_OR_CHANGE_STATUS_WORK_OBJECT_MESSAGE);
                return "error/work_object-error";
            } else {
                workObjectService.saveWorkObject(workObject);
            }
        }
        return "redirect:/general/workobject/workobjectView/%d".formatted(workObject.getWorkObjectId());
    }
}