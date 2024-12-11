package com.example.arrowdb.controllers;

import com.example.arrowdb.auxiliary.MailSenderService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;

import static com.example.arrowdb.auxiliary.Message.ERROR_CREATE_NEW_USER;
import static com.example.arrowdb.auxiliary.PassGenerator.randomPass;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;
    private final MailSenderService mailSenderService;

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
        model.addAttribute("employeeList", employeeService.findEmployeeByParameters(2).stream()
                .sorted(Comparator.comparingInt(Employee::getEmpId))
                .toList());
        model.addAttribute("rolesAdmin", roleRepository.findRolesByMenuName("admin"));
        model.addAttribute("rolesPersonal", roleRepository.findRolesByMenuName("personal"));
        model.addAttribute("rolesStore", roleRepository.findRolesByMenuName("store"));
        model.addAttribute("rolesActivity", roleRepository.findRolesByMenuName("activity"));
        model.addAttribute("statusList", UserStatusENUM.values());
        return "user/user-create";
    }

    @PostMapping("/general/users/userCreate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createUser(@Valid @ModelAttribute Users users, Model model) {
        String login = users.getEmployee().getName().substring(0, 1).toUpperCase()
                + users.getEmployee().getMiddleName().substring(0, 1).toUpperCase() + "_"
                + users.getEmployee().getSurName() + "_" + users.getEmployee().getEmpId();
        users.setUserName(login);
        users.getEmployee().setLogin(login);
        String tempPassword = randomPass();
        users.setPassword(tempPassword);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);
        try {
            mailSenderService.send(
                    employeeService.findEmailByLogin(users.getUserName()),
                    "Создание данных для входа в систему",
                    String.format("Логин: %s%nПароль: %s%nСтатус учетной записи: " +
                                    "%s%nАдрес: http://192.168.3.250:8080/login",
                            users.getUserName(),
                            tempPassword,
                            users.getUserStatusENUM().getTitle()));
        } catch (Exception e) {
            users.setPassword(null);
            users.getEmployee().setLogin(null);
            usersRepository.deleteById(users.getUserId());
            model.addAttribute("employeeList", employeeService.findEmployeeByParameters(2).stream()
                    .sorted(Comparator.comparingInt(Employee::getEmpId))
                    .toList());
            model.addAttribute("rolesAdmin", roleRepository.findRolesByMenuName("admin"));
            model.addAttribute("rolesPersonal", roleRepository.findRolesByMenuName("personal"));
            model.addAttribute("rolesStore", roleRepository.findRolesByMenuName("store"));
            model.addAttribute("rolesActivity", roleRepository.findRolesByMenuName("activity"));
            model.addAttribute("statusList", UserStatusENUM.values());
            model.addAttribute("error", ERROR_CREATE_NEW_USER);
            return "user/user-create";
        }
        return "redirect:/general/users";
    }

    @GetMapping("/general/users/userDelete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") int id) {
        Users users = usersRepository.findById(id).orElse(null);
        assert users != null;
        users.getEmployee().setLogin(null);
        usersRepository.deleteById(id);
        return "redirect:/general/users";
    }

    @GetMapping("/general/users/userUpdate/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUserForm(@PathVariable("id") int id,
                                 Model model) {
        Users users = usersRepository.findById(id).orElse(null);
        model.addAttribute("users", users);
        model.addAttribute("rolesAdmin", roleRepository.findRolesByMenuName("admin"));
        model.addAttribute("rolesPersonal", roleRepository.findRolesByMenuName("personal"));
        model.addAttribute("rolesStore", roleRepository.findRolesByMenuName("store"));
        model.addAttribute("rolesActivity", roleRepository.findRolesByMenuName("activity"));
        assert users != null;
        if (users.getEmployee().getEmployeeStatusENUM().getTitle().equals("Закрыт")) {
            model.addAttribute("statusList", Arrays.stream(UserStatusENUM.values())
                    .filter(e -> !e.getTitle().equals("Действует")));
        } else {
            model.addAttribute("statusList", UserStatusENUM.values());
        }
        return "user/user-update";
    }

    @PostMapping("/general/users/userUpdate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUser(@Valid Users users) {
        usersRepository.save(users);
        return "redirect:/general/users";
    }

    @GetMapping("/general/users/recovery/{id}")
    public String sendMessage(@PathVariable("id") int id, Model model) {
        Users users = usersRepository.findById(id).orElseThrow();
        String tempPassword = randomPass();
        users.setPassword(tempPassword);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        if (users.getEmployee().getEmployeeStatusENUM().getTitle().equals("Закрыт")) {
            model.addAttribute("users", usersRepository.findAll().stream()
                    .sorted(Comparator.comparingInt(Users::getUserId))
                    .toList());
            model.addAttribute("error", "ОШИБКА! У работника " + users.getEmployee() +
                    ", " + users.getEmployee().getProfession().getProfessionName() + " отсутствует (удален) e-mail, " +
                    "и/или работник находится в статусе Закрыт, восстановить учетную запись возможности нет");
            return "user/user-menu";
        }
        try {
            mailSenderService.send(
                    employeeService.findEmailByLogin(users.getUserName()),
                    "Восстановление данных для входа в систему",
                    String.format("Логин: %s%nПароль: %s%nСтатус учетной записи: " +
                                    "%s%nАдрес: http://192.168.3.250:8080/login",
                            users.getUserName(),
                            tempPassword,
                            users.getUserStatusENUM().getTitle()));
            usersRepository.save(users);
        } catch (Exception e) {
            model.addAttribute("users", usersRepository.findAll().stream()
                    .sorted(Comparator.comparingInt(Users::getUserId))
                    .toList());
            model.addAttribute("error", "ОШИБКА! У работника " + users.getEmployee() +
                    ", " + users.getEmployee().getProfession().getProfessionName() + " отсутствует (удален) e-mail, " +
                    "и/или работник находится в статусе Закрыт, восстановить учетную запись возможности нет");
            return "user/user-menu";
        }
        return "redirect:/general/users";
    }
}