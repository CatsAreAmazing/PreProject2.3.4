package ru.kata.spring.boot_security.demo.controller;

import org.springframework.boot.Banner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("newUser", new User());
        return "users";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult, Model model) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "edit";
    }

    @PutMapping("/update")
    public String update(@ModelAttribute("user") @Valid User user, @RequestParam("id") Integer id) {
        //я так и не понял как это делать адекватно, так что сделаю топорно
        User oldUser = userService.findOne(id);
        if (Objects.equals(user.getPassword(), oldUser.getPassword())) {
            userService.updateWithoutPassword(id, user);
        } else {
            userService.update(id, user);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
