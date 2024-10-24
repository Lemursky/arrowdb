package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.Employee;
import com.example.arrowdb.entity.PersonalInstrument;
import com.example.arrowdb.entity.WorkInstrument;
import com.example.arrowdb.history.EmployeeAUD;
import com.example.arrowdb.history.PersonalInstrumentAUD;
import com.example.arrowdb.history.WorkInstrumentAUD;
import com.example.arrowdb.repositories.EmployeeAUDRepository;
import com.example.arrowdb.repositories.PersonalInstrumentAUDRepository;
import com.example.arrowdb.repositories.WorkInstrumentAUDRepository;
import com.example.arrowdb.services.EmployeeService;
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

    @GetMapping("/general/employee/employeeJournal/{id}")
    public String getEmployeeJournal(@PathVariable("id") int id, Model model){
        Employee employee = employeeService.findEmployeeById(id);
        List<EmployeeAUD> employeeAUDList = employeeAUDRepository.findAllEmployeeAUDByEmployeeId(id);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeAUDList", employeeAUDList);
        return "employee/employee_event-journal";
    }

    @GetMapping("/general/p_instrument/p_instrumentJournal/{id}")
    public String getPersonalInstrumentJournal(@PathVariable("id") int id, Model model){
        PersonalInstrument personalInstrument = personalInstrumentService.findPersonalInstrumentById(id);
        List<PersonalInstrumentAUD> personalInstrumentAUDList = personalInstrumentAUDRepository
                .findAllPersonalInstrumentAUDById(id);
        model.addAttribute("personalInstrument", personalInstrument);
        model.addAttribute("personalInstrumentAUDList", personalInstrumentAUDList);
        return "stock/p_instrument_event-journal";
    }

    @GetMapping("/general/w_instrument/w_instrumentJournal/{id}")
    public String getWorkInstrumentJournal(@PathVariable("id") int id, Model model){
        WorkInstrument workInstrument = workInstrumentService.findWorkInstrumentById(id);
        List<WorkInstrumentAUD> workInstrumentAUDList = workInstrumentAUDRepository.findAllWorkInstrumentAUDById(id);
        model.addAttribute("workInstrument", workInstrument);
        model.addAttribute("workInstrumentAUDList", workInstrumentAUDList);
        return "stock/w_instrument_event-journal";
    }
}