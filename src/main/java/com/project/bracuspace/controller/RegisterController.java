package com.project.bracuspace.controller;

import com.project.bracuspace.dto.UserDTO;
import com.project.bracuspace.model.User;
import com.project.bracuspace.service.email.EmailService;
import com.project.bracuspace.service.user.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping
public class RegisterController {
    private final UserService userService;
    private final EmailService emailService;

    public RegisterController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/process")
    public ModelAndView saveUser(ModelAndView modelAndView, @ModelAttribute("UserDTO") @Valid final UserDTO UserDTO, BindingResult bindingResult, HttpServletRequest request, Errors errors) {

        User emailExists = userService.findByEmail(UserDTO.getEmail());
        User userNameExists = userService.findByUsername(UserDTO.getUsername());

        if (emailExists != null) {
            modelAndView.setViewName("public/register");
            bindingResult.rejectValue("email", "emailExists");
        }

        if (userNameExists != null) {
            modelAndView.setViewName("public/register");
            bindingResult.rejectValue("username", "userNameExists");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("public/register");
        } else {
            User user = userService.createNewAccount(UserDTO);
            user.setEnabled(true);
            userService.save(user);

            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("BRACU Space Account Created");
            registrationEmail.setText("Your account has been created. You can start using the storage service by " + "login to page. Thank you for using BRACU Space.");
            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("message", "Email has been sent to " + UserDTO.getEmail() + ".");
            modelAndView.setViewName("public/process");
        }
        return modelAndView;
    }
}
