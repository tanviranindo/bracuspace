package com.project.bracuspace.controller;

import com.project.bracuspace.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping
public class IndexController {
    public IndexController() {
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "public/index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "public/login";
    }

    @GetMapping(value = "/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDTO UserDTO = new UserDTO();
        model.addAttribute("UserDTO", UserDTO);
        return "public/register";
    }
}
