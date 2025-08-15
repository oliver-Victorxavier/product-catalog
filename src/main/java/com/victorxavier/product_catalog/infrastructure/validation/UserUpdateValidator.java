package com.victorxavier.product_catalog.infrastructure.validation;

import com.victorxavier.product_catalog.domain.dto.UserUpdateDto;
import com.victorxavier.product_catalog.domain.entity.User;
import com.victorxavier.product_catalog.domain.exception.FieldMessage;
import com.victorxavier.product_catalog.domain.repository.UserRepository;
import com.victorxavier.product_catalog.domain.validation.UserUpdateValid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDto> {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private UserRepository repository;
    
    @Override
    public void initialize(UserUpdateValid ann) {
    }
    
    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {
        
        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String userId = uriVars.get("id");
        
        List<FieldMessage> list = new ArrayList<>();
        
        Optional<User> user = repository.findByUsername(dto.username());
        if (user.isPresent() && !userId.equals(user.get().getUserId())) {
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