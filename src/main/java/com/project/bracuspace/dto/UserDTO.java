package com.project.bracuspace.dto;

import com.project.bracuspace.validator.PasswordValidate;
import com.project.bracuspace.validator.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordValidate
public class UserDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String surname;

    @NotBlank(message = "Username is required")
    private String username;

    @ValidEmail
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Matching password is required")
    private String matchingPassword;

    private boolean enabled;
}
