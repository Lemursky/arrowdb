package com.example.arrowdb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class GeneralController {

    @GetMapping("/general")
    public String genMenu(@AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        model.addAttribute("userName", userDetails.getUsername());
        model.addAttribute("currentDate", LocalDate.now());
        return "general/general-menu";
    }
}