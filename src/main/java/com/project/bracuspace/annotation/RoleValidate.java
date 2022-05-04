package com.project.bracuspace.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidate implements ConstraintValidator<ValidRole, String> {
    private static final String VALID_ROLE_NAME_PREFIX = "ROLE_";

    @Override
    public void initialize(ValidRole validRole) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.startsWith(VALID_ROLE_NAME_PREFIX);
    }

}
