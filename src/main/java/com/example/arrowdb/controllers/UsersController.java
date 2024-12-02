package com.example.arrowdb.controllers;

import com.example.arrowdb.entity.*;
import com.example.arrowdb.enums.UserStatusENUM;
import com.example.arrowdb.repositories.*;
import com.example.arrowdb.services.EmployeeService;
import jakarta.validation.Valid;
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
    private final EmployeeService employeeService;

    @GetMapping("/general/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Users::getUserId))
                .toList());
        return "user/user-menu";
    }

    @GetMapping("/general/users/userCreate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createUserForm(@ModelAttribute Users users,
                                 Model model) {
        model.addAttribute("employeeList", employeeService.findAllEmployees().stream()
                .filter(e -> e.getEmployeeStatusENUM().getTitle().contains("Действующий"))
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList());
        model.addAttribute("roles", roleRepository.findAll());
        return "user/user-create";
    }

    @PostMapping("/general/users/userCreate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createUser(@Valid @ModelAttribute Users users,
                             BindingResult bindingResult,
                             Model model) {
        List<Roles> roles = roleRepository.findAll();
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roles);
            return "user/user-create";
        } else {
            users.setUserStatusENUM(UserStatusENUM.OFF);
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
                                 Model model) {
        model.addAttribute("users", usersRepository.findById(id).orElseThrow());
        model.addAttribute("rolesAdmin", roleRepository.findRolesByMenuName("admin"));
        model.addAttribute("rolesPersonal", roleRepository.findRolesByMenuName("personal"));
        model.addAttribute("rolesStore", roleRepository.findRolesByMenuName("store"));
        model.addAttribute("rolesActivity", roleRepository.findRolesByMenuName("activity"));
        model.addAttribute("statusList", UserStatusENUM.values());
        return "user/user-update";
    }

    @PostMapping("/general/users/userUpdate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUser(@Valid Users users) {
        usersRepository.save(users);
        return "redirect:/general/users";
    }
}