package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.repositories.*;
import com.example.arrowdb.services.UserStatusService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserStatusService userStatusService;

    @GetMapping("/general/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAllUsers(@NotNull Model model) {
        List<Users> users = usersRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Users::getUserId))
                .toList();
        model.addAttribute("users", users);
        return "user/user-menu";
    }

    @GetMapping("/general/users/userCreate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createUserForm(@ModelAttribute Users users,
                                 @NotNull Model model) {
        List<Roles> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "user/user-create";
    }

    @PostMapping("/general/users/userCreate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createUser(@Valid Users users,
                             @NotNull BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            List<Roles> roles = roleRepository.findAll();
            model.addAttribute("roles", roles);
            return "user/user-create";
        } else {
            users.setUserStatus(userStatusService.findStatusById(2));
            List<Roles> roles = roleRepository.findAll();
            model.addAttribute("users", users);
            model.addAttribute("roles", roles);
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            usersRepository.save(users);
            return "redirect:/general/users";
        }
    }

    @GetMapping("/general/users/userDelete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") int id) {
        usersRepository.deleteById(id);
        return "redirect:/general/users";
    }

    @GetMapping("/general/users/userUpdate/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUserForm(@PathVariable("id") int id,
                                 @NotNull Model model) {
        Users users = usersRepository.findById(id).orElseThrow();
        List<Roles> rolesAdmin = roleRepository.findRolesByMenuName("admin");
        List<Roles> rolesPersonal = roleRepository.findRolesByMenuName("personal");
        List<Roles> rolesStore = roleRepository.findRolesByMenuName("store");
        List<Roles> rolesActivity = roleRepository.findRolesByMenuName("activity");
        List<UserStatus> statusList = userStatusService.findAllUserStatus();
        model.addAttribute("users", users);
        model.addAttribute("rolesAdmin", rolesAdmin);
        model.addAttribute("rolesPersonal", rolesPersonal);
        model.addAttribute("rolesStore", rolesStore);
        model.addAttribute("rolesActivity", rolesActivity);
        model.addAttribute("statusList", statusList);
        return "user/user-update";
    }

    @PostMapping("/general/users/userUpdate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUser(@Valid Users users) {
        usersRepository.saveAndFlush(users);
        return "redirect:/general/users";
    }
}