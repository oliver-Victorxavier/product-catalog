package com.victorxavier.product_catalog.infrastructure.persistence.repository.impl;

import com.victorxavier.product_catalog.infrastructure.persistence.mapper.passwordrecover.PasswordRecoverMapper;
import com.victorxavier.product_catalog.domain.entity.PasswordRecover;
import com.victorxavier.product_catalog.domain.repository.PasswordRecoverRepository;
import com.victorxavier.product_catalog.infrastructure.persistence.repository.JpaPasswordRecoverRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class PasswordRecoverRepositoryImpl implements PasswordRecoverRepository {
    
    private final JpaPasswordRecoverRepository jpaRepository;
    private final PasswordRecoverMapper mapper;
    
    public PasswordRecoverRepositoryImpl(JpaPasswordRecoverRepository jpaRepository, PasswordRecoverMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public PasswordRecover save(PasswordRecover passwordRecover) {
        var entity = mapper.toEntity(passwordRecover);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<PasswordRecover> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<PasswordRecover> searchValidTokens(String token, Instant now) {
        return jpaRepository.searchValidTokens(token, now)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}