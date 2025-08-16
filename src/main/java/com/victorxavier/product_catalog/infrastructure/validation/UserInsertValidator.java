package com.victorxavier.product_catalog.infrastructure.validation;

import com.victorxavier.product_catalog.domain.dto.UserInsertDTO;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.infrastructure.validation.UserInsertValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        boolean valid = true;

        if (dto.email() != null && userRepository.findByEmail(dto.email()).isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Email já está em uso")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            valid = false;
        }

        if (dto.username() != null && userRepository.findByUsername(dto.username()).isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username já está em uso")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}