package ru.kata.spring.boot_security.demo.controller;

import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.io.Console;
import java.security.Principal;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }
    @GetMapping("/viewUser")
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok(userService.findByName(principal.getName()));
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> index() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/user")
    public User show(@RequestParam("id") Integer id) {
        return userService.findOne(id);
    }



    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid User user) {
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid User user, @RequestParam("id") Integer id) {
        //я так и не понял как это делать адекватно, так что сделаю топорно
        User oldUser = userService.findOne(id);
        if (Objects.equals(user.getPassword(), oldUser.getPassword())) {
            userService.updateWithoutPassword(id, user);
        } else {
            userService.update(id, user);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@RequestParam("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
