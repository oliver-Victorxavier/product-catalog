package com.victorxavier.product_catalog.infrastructure.validation;

import com.victorxavier.product_catalog.domain.dto.UserInsertDTO;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.exception.FieldMessage;
import com.victorxavier.product_catalog.domain.validation.UserInsertValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
    
    @Autowired
    private UserRepository repository;
    
    @Override
    public void initialize(UserInsertValid ann) {
    }
    
    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
        
        List<FieldMessage> list = new ArrayList<>();
        
        Optional<User> user = repository.findByUsername(dto.username());
        if (user.isPresent()) {
            list.add(new FieldMessage("username", "Username já existe"));
        }
        
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}