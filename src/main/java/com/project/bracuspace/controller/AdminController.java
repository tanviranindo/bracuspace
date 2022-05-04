package com.project.bracuspace.controller;

import com.project.bracuspace.dto.UserDTO;
import com.project.bracuspace.dto.UserUpdateDTO;
import com.project.bracuspace.model.Role;
import com.project.bracuspace.model.User;
import com.project.bracuspace.service.role.RoleService;
import com.project.bracuspace.service.searching.SearchParameters;
import com.project.bracuspace.service.searching.UserFinder;
import com.project.bracuspace.service.searching.UserSearchErrorResponse;
import com.project.bracuspace.service.searching.UserSearchResult;
import com.project.bracuspace.service.user.UserDTOService;
import com.project.bracuspace.service.user.UserService;
import com.project.bracuspace.service.user.UserUpdateDTOService;
import com.project.bracuspace.web.Pager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.project.bracuspace.web.PagingSize.*;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserUpdateDTOService UserUpdateDTOService;
    private final UserDTOService UserDTOService;
    private final UserFinder userFinder;
    private final UserSearchErrorResponse userSearchErrorResponse;

    public AdminController(UserService userService, RoleService roleService, UserUpdateDTOService userUpdateDTOService, UserDTOService UserDTOService, UserFinder userFinder, UserSearchErrorResponse userSearchErrorResponse) {
        this.userService = userService;
        this.roleService = roleService;
        this.UserUpdateDTOService = userUpdateDTOService;
        this.UserDTOService = UserDTOService;
        this.userFinder = userFinder;
        this.userSearchErrorResponse = userSearchErrorResponse;
    }

    @GetMapping
    public ModelAndView admin() {
        return new ModelAndView("redirect:/admin/users");
    }

    @GetMapping("/users")
    public ModelAndView getUsers(ModelAndView modelAndView, SearchParameters searchParams) {
        int selectedPageSize = searchParams.getPageSize().orElse(INITIAL_PAGE_SIZE);
        int selectedPage = (searchParams.getPage().orElse(0) < 1) ? INITIAL_PAGE : (searchParams.getPage().get() - 1);

        PageRequest pageRequest = of(selectedPage, selectedPageSize, Sort.by(ASC, "id"));
        UserSearchResult userSearchResult = new UserSearchResult();

        if (searchParams.getPropertyValue().isEmpty() || searchParams.getPropertyValue().get().isEmpty())
            userSearchResult.setUserPage(UserDTOService.findAllPageable(pageRequest));

        else {
            userSearchResult = userFinder.searchUsersByProperty(pageRequest, searchParams);

            if (userSearchResult.isNumberFormatException())
                return userSearchErrorResponse.respondToNumberFormatException(userSearchResult, modelAndView);

            if (userSearchResult.getUserPage().getTotalElements() == 0) {
                modelAndView = userSearchErrorResponse.respondToEmptySearchResult(modelAndView, pageRequest);
                userSearchResult.setUserPage(UserDTOService.findAllPageable(pageRequest));
            }
            modelAndView.addObject("usersProperty", searchParams.getUsersProperty().get());
            modelAndView.addObject("propertyValue", searchParams.getPropertyValue().get());
        }

        Pager pager = new Pager(userSearchResult.getUserPage().getTotalPages(), userSearchResult.getUserPage().getNumber(), BUTTONS_TO_SHOW, userSearchResult.getUserPage().getTotalElements());
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("users", userSearchResult.getUserPage());
        modelAndView.addObject("selectedPageSize", selectedPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.setViewName("admin/user/users");
        return modelAndView;
    }

    @GetMapping("/users/{id}")
    public String getEditUserForm(@PathVariable Long id, Model model) {
        UserUpdateDTO userUpdateDTO = UserUpdateDTOService.findById(id);
        List<Role> allRoles = roleService.findAll();

        userUpdateDTO.setRoles(userService.getAssignedRolesList(userUpdateDTO));

        model.addAttribute("userUpdateDTO", userUpdateDTO);
        model.addAttribute("allRoles", allRoles);
        return "admin/user/editUser";
    }

    @PostMapping("/users/{id}")
    public String updateUser(Model model, @PathVariable Long id, @ModelAttribute("oldUser") @Valid UserUpdateDTO userUpdateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Optional<User> persistedUser = userService.findById(id);
        List<Role> allRoles = roleService.findAll();

        boolean emailAlreadyExists = userService.findByEmailAndIdNot(userUpdateDTO.getEmail(), id) != null;
        boolean usernameAlreadyExists = userService.findByUsernameAndIdNot(userUpdateDTO.getUsername(), id) != null;
        boolean validationFailed = emailAlreadyExists || usernameAlreadyExists || bindingResult.hasErrors();

        if (emailAlreadyExists) bindingResult.rejectValue("email", "emailExists");
        if (usernameAlreadyExists) bindingResult.rejectValue("username", "userNameExists");
        if (validationFailed) {
            model.addAttribute("userUpdateDTO", userUpdateDTO);
            model.addAttribute("rolesList", allRoles);
            model.addAttribute("org.springframework.validation.BindingResult.userUpdateDTO", bindingResult);
            return "admin/user/editUser";
        }
        userService.save(userService.getUpdatedUser(persistedUser.get(), userUpdateDTO));
        redirectAttributes.addFlashAttribute("userHasBeenUpdated", true);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/newUser")
    public String getAddNewUserForm(Model model) {
        model.addAttribute("newUser", new UserDTO());
        return "admin/user/newUser";
    }

    @PostMapping("/users/newUser")
    public String saveNewUser(@ModelAttribute("newUser") @Valid UserDTO newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        boolean emailAlreadyExists = userService.findByEmail(newUser.getEmail()) != null;
        boolean usernameAlreadyExists = userService.findByUsername(newUser.getUsername()) != null;
        boolean validationFailed = emailAlreadyExists || usernameAlreadyExists || bindingResult.hasErrors();
        String formWithErrors = "admin/user/newUser";

        if (emailAlreadyExists) bindingResult.rejectValue("email", "emailExists");
        if (usernameAlreadyExists) bindingResult.rejectValue("username", "userNameExists");
        if (validationFailed) return formWithErrors;

        User user = userService.createNewAccount(newUser);
        user.setEnabled(true);

        userService.save(user);

        redirectAttributes.addFlashAttribute("userHasBeenSaved", true);
        return "redirect:/admin/users";
    }
}
