package com.project.bracuspace.dto;

import com.project.bracuspace.annotation.ValidRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleDTO {
    Long id;

    @ValidRole
    @NotBlank(message = "Role is required") String name;
}
