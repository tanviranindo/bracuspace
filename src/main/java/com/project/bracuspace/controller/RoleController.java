package com.project.bracuspace.controller;

import com.project.bracuspace.dto.RoleDTO;
import com.project.bracuspace.model.Role;
import com.project.bracuspace.service.role.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ModelAndView showRoles() {
        ModelAndView modelAndView = new ModelAndView("admin/role/roles");
        modelAndView.addObject("roles", roleService.findAll());
        return modelAndView;
    }

    @GetMapping("/roles/{id}")
    public ModelAndView getEditRoleForm(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        ModelAndView modelAndView = new ModelAndView("admin/role/editRole");
        role.ifPresent(value -> modelAndView.addObject("role", value));
        return modelAndView;
    }

    @PostMapping("/roles/{id}")
    public String updateRole(Model model, @PathVariable Long id, @ModelAttribute("oldRole") @Valid final Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Optional<Role> persistedRole = roleService.findById(id);
        List<Role> allRoles = roleService.findAll();
        boolean roleNameAlreadyExists = roleService.checkIfRoleNameIsTaken(allRoles, role, persistedRole.get());
        boolean hasErrors = roleNameAlreadyExists || bindingResult.hasErrors();

        if (roleNameAlreadyExists) bindingResult.rejectValue("name", "roleExists");

        if (hasErrors) {
            model.addAttribute("role", role);
            model.addAttribute("org.springframework.validation.BindingResult.role", bindingResult);
            return "admin/role/editRole";
        }
        roleService.save(role);
        redirectAttributes.addFlashAttribute("roleHasBeenUpdated", true);
        return "redirect:/admin/roles";
    }

    @GetMapping("/roles/newRole")
    public String getAddNewRoleForm(Model model) {
        model.addAttribute("newRole", new RoleDTO());
        return "admin/role/newRole";
    }

    @PostMapping("/roles/newRole")
    public String saveNewRole(@ModelAttribute("newRole") @Valid final Role newRole, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        boolean roleNameAlreadyExists = roleService.findByName(newRole.getName()) != null;
        boolean hasErrors = roleNameAlreadyExists || bindingResult.hasErrors();
        String formWithErrors = "admin/role/newRole";

        if (roleNameAlreadyExists) bindingResult.rejectValue("name", "roleExists");
        if (hasErrors) return formWithErrors;

        roleService.save(newRole);
        redirectAttributes.addFlashAttribute("roleHasBeenSaved", true);
        return "redirect:/admin/roles";
    }
}
