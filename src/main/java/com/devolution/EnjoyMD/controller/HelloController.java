package com.devolution.EnjoyMD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", "Thymeleaf");
        return "main";
    }
}
