package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.Users;
import com.example.arrowdb.repositories.RoleRepository;
import com.example.arrowdb.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
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