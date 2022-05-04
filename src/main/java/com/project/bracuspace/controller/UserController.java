package com.project.bracuspace.controller;


import com.project.bracuspace.model.User;
import com.project.bracuspace.service.storage.StorageService;
import com.project.bracuspace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String viewAccount() {
        return "user/account";
    }

    private final UserService userService;
    private final StorageService storageService;

    @Autowired
    public UserController(UserService userService, StorageService storageService) {
        this.userService = userService;
        this.storageService = storageService;
    }

    @GetMapping("/space")
    public String viewSpace(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("fileList", storageService.getFiles(user.getUsername()));
        return "user/space";
    }

    @GetMapping("/remote")
    public String remoteTransfer() {
        return "user/remote";
    }

    @GetMapping("/transfer")
    public String viewTransfer() {
        return "user/transfer";
    }

    @GetMapping("/account")
    public ModelAndView viewAccount(Authentication authentication, ModelAndView modelAndView, Model model) {
        User user = userService.findByUsername(authentication.getName());
        StringBuilder roleList = new StringBuilder();
        int count = 0;
        for (GrantedAuthority access : authentication.getAuthorities()) {
            if (count + 1 == authentication.getAuthorities().size()) {
                roleList.append(access.getAuthority());
            } else {
                roleList.append(access.getAuthority()).append(", ");
            }
            count++;
        }

        model.addAttribute("firstName", user.getName());
        model.addAttribute("lastName", user.getUsername());
        model.addAttribute("handle", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("roles", roleList.toString().replaceAll("ROLE_", ""));
        model.addAttribute("accountStatus", user.isEnabled() ? "Verified" : "Not verified");

        modelAndView.setViewName("user/account");
        return modelAndView;
    }
}