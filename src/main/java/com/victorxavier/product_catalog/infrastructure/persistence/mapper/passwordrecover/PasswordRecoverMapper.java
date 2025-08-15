package com.victorxavier.product_catalog.infrastructure.persistence.mapper.passwordrecover;

import com.victorxavier.product_catalog.domain.entity.PasswordRecover;
import com.victorxavier.product_catalog.infrastructure.persistence.entity.PasswordRecoverEntity;
import org.springframework.stereotype.Component;

@Component
public class PasswordRecoverMapper {
    
    public PasswordRecover toDomain(PasswordRecoverEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PasswordRecover(
            entity.getId(),
            entity.getToken(),
            entity.getEmail(),
            entity.getExpiration()
        );
    }
    
    public PasswordRecoverEntity toEntity(PasswordRecover domain) {
        if (domain == null) {
            return null;
        }
        return new PasswordRecoverEntity(
            domain.getId(),
            domain.getToken(),
            domain.getEmail(),
            domain.getExpiration()
        );
    }
}