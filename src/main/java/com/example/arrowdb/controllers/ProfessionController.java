package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.QualityENUM;
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

import java.util.Comparator;
import java.util.List;

import static com.example.arrowdb.message.Message.*;

@Controller
@RequiredArgsConstructor
public class ProfessionController {

    private final ProfessionService professionService;

    @GetMapping("/general/profession")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PROFESSION_VIEW')")
    public String getProfessionsList(@NotNull Model model) {
        model.addAttribute("profession", professionService.findAllProfessions().stream()
                .sorted(Comparator.comparingInt((Profession::getProfId)))
                .toList());
        return "profession/profession-menu";
    }

    @GetMapping("/general/profession/professionView/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PROFESSION_VIEW')")
    public String findProfessionById(@PathVariable("id") int id,
                                     Model model) {
        model.addAttribute("profession", professionService.findProfessionById(id));
        return "profession/profession-view";
    }

    @GetMapping("/general/profession/professionCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PROFESSION_CREATE')")
    public String createProfessionForm(@ModelAttribute Profession profession,
                                       Model model) {
        model.addAttribute("qualityList", QualityENUM.values());
        return "profession/profession-create";
    }

    @PostMapping("/general/profession/professionCreate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PROFESSION_CREATE')")
    public String createProfession(@Valid @ModelAttribute Profession profession,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            return "profession/profession-create";
        } else {
            try {
                professionService.saveProfession(profession);
                return "redirect:/general/profession";
            } catch (Exception e) {
                model.addAttribute("error", UNIQUE_PROFESSION);
                return "profession/profession-create";
            }
        }
    }

    @GetMapping("/general/profession/professionDelete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PROFESSION_DELETE')")
    public String deleteProfession(@PathVariable("id") int id,
                                   Model model) {
        try {
            professionService.deleteProfessionById(id);
        } catch (Exception e) {
            Profession profession = professionService.findProfessionById(id);
            model.addAttribute("profession", profession);
            model.addAttribute("error", DELETE_PROFESSION_MESSAGE);
            return "error/profession-error";
        }
        return "redirect:/general/profession";
    }

    @GetMapping("/general/profession/professionUpdate/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PROFESSION_UPDATE')")
    public String updateProfessionForm(@PathVariable("id") int id,
                                       Model model) {
        model.addAttribute("profession", professionService.findProfessionById(id));
        model.addAttribute("qualityList", QualityENUM.values());
        return "profession/profession-update";
    }

    @PostMapping("/general/profession/professionUpdate")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PROFESSION_UPDATE')")
    public String updateProfession(@Valid @ModelAttribute Profession profession,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("qualityList", QualityENUM.values());
            return "profession/profession-update";
        } else {
            try {
                model.addAttribute("qualityList", QualityENUM.values());
                professionService.saveProfession(profession);
                return "redirect:/general/profession/professionView/%d".formatted(profession.getProfId());
            } catch (Exception e) {
                model.addAttribute("error", UNIQUE_PROFESSION);
                return "profession/profession-update";
            }
        }
    }

}