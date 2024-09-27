package com.devolution.EnjoyMD.controllers;

import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@Controller
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if(!userService.registerUser(user)) {
            model.addAttribute("errorMessage", "User with this email or username already exists!");
            return "register";
        }

        return "redirect:/main";
    }
}
