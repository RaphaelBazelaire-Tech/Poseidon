package com.nnk.springboot.controller;

import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addForm(@ModelAttribute("user") UserModel user) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user") UserModel user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        UserModel user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") UserModel user,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "user/update";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        userService.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}
