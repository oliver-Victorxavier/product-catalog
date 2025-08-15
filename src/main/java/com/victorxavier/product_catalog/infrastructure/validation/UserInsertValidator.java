package com.victorxavier.product_catalog.infrastructure.validation;

import com.victorxavier.product_catalog.domain.dto.CreateUserDto;
import com.victorxavier.product_catalog.infrastructure.validation.UserInsertValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, CreateUserDto> {

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(CreateUserDto dto, ConstraintValidatorContext context) {
        // Implementar validações específicas se necessário
        return true;
    }
}