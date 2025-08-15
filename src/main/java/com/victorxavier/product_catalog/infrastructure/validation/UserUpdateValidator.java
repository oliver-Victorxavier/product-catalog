package com.victorxavier.product_catalog.infrastructure.validation;

import com.victorxavier.product_catalog.domain.dto.UserUpdateDto;
import com.victorxavier.product_catalog.infrastructure.validation.UserUpdateValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDto> {

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {
        // Implementar validações específicas se necessário
        return true;
    }
}