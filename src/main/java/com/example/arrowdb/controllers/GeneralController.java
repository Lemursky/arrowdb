package com.example.arrowdb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class GeneralController {

    @GetMapping("/general")
    public String genMenu(Principal principal, Model model) {
        String name = principal.getName();
        model.addAttribute("name", name);
        model.addAttribute("currentDate", LocalDate.now());
        return "general/general-menu";
    }
}