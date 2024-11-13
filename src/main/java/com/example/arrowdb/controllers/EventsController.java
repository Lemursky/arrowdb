package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.MeasInstrument;
import com.example.arrowdb.entity.PersonalInstrument;
import com.example.arrowdb.entity.WorkInstrument;
import com.example.arrowdb.history.EmployeeAUD;
import com.example.arrowdb.history.MeasInstrumentAUD;
import com.example.arrowdb.history.PersonalInstrumentAUD;
import com.example.arrowdb.history.WorkInstrumentAUD;
import com.example.arrowdb.repositories.EmployeeAUDRepository;
import com.example.arrowdb.repositories.MeasInstrumentAUDRepository;
import com.example.arrowdb.repositories.PersonalInstrumentAUDRepository;
import com.example.arrowdb.repositories.WorkInstrumentAUDRepository;
import com.example.arrowdb.services.EmployeeService;
import com.example.arrowdb.services.MeasInstrumentService;
import com.example.arrowdb.services.PersonalInstrumentService;
import com.example.arrowdb.services.WorkInstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
}