package com.example.arrowdb.controllers;

import com.example.arrowdb.repositories.*;
import com.example.arrowdb.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class EventsController {

    private final EmployeeService employeeService;
    private final EmployeeAUDRepository employeeAUDRepository;
    private final PersonalInstrumentService personalInstrumentService;
    private final PersonalInstrumentAUDRepository personalInstrumentAUDRepository;
    private final WorkInstrumentService workInstrumentService;
    private final WorkInstrumentAUDRepository workInstrumentAUDRepository;
    private final MeasInstrumentService measInstrumentService;
    private final MeasInstrumentAUDRepository measInstrumentAUDRepository;
    private final ConstructionControlService constructionControlService;
    private final ConstructionControlAUDRepository constructionControlAUDRepository;
    private final WorkObjectService workObjectService;
    private final WorkObjectAUDRepository workObjectAUDRepository;

    @GetMapping("/general/employee/employeeJournal/{id}")
    public String getEmployeeJournal(@PathVariable("id") int id, Model model){
        model.addAttribute("employee", employeeService
                .findEmployeeById(id));
        model.addAttribute("employeeAUDList", employeeAUDRepository
                .findAllEmployeeAUDByEmployeeId(id));
        return "employee/employee_event-journal";
    }

    @GetMapping("/general/p_instrument/p_instrumentJournal/{id}")
    public String getPersonalInstrumentJournal(@PathVariable("id") int id, Model model){
        model.addAttribute("personalInstrument", personalInstrumentService
                .findPersonalInstrumentById(id));
        model.addAttribute("personalInstrumentAUDList", personalInstrumentAUDRepository
                .findAllPersonalInstrumentAUDById(id));
        return "stock/p_instrument_event-journal";
    }

    @GetMapping("/general/w_instrument/w_instrumentJournal/{id}")
    public String getWorkInstrumentJournal(@PathVariable("id") int id, Model model){
        model.addAttribute("workInstrument", workInstrumentService
                .findWorkInstrumentById(id));
        model.addAttribute("workInstrumentAUDList", workInstrumentAUDRepository
                .findAllWorkInstrumentAUDById(id));
        return "stock/w_instrument_event-journal";
    }

    @GetMapping("/general/m_instrument/m_instrumentJournal/{id}")
    public String getMeasInstrumentJournal(@PathVariable("id") int id, Model model){
        model.addAttribute("measInstrument", measInstrumentService
                .findMeasInstrumentById(id));
        model.addAttribute("measInstrumentAUDList", measInstrumentAUDRepository
                .findAllMeasInstrumentAUDById(id));
        return "stock/m_instrument_event-journal";
    }

    @GetMapping("/general/constr_control/constr_controlJournal/{id}")
    public String getConstructionControlJournal(@PathVariable("id") int id, Model model){
        model.addAttribute("constructionControl", constructionControlService
                .findConstructionControlById(id));
        model.addAttribute("constructionControlAUDList", constructionControlAUDRepository
                .findAllConstructionControlAUDById(id));
        return "constr_control/c_control_event-journal";
    }

    @GetMapping("/general/workobject/workobjectJournal/{id}")
    public String getWorkObjectJournal(@PathVariable("id") int id, Model model){
        model.addAttribute("workObject", workObjectService
                .findWorkObjectById(id));
        model.addAttribute("workObjectAUDList", workObjectAUDRepository
                .findAllWorkObjectAUDById(id));
        return "work_object/work_object-jounal";
    }
}