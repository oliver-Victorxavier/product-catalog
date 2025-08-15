package com.victorxavier.product_catalog.infrastructure.persistence.repository;

import com.victorxavier.product_catalog.infrastructure.persistence.entity.PasswordRecoverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface JpaPasswordRecoverRepository extends JpaRepository<PasswordRecoverEntity, Long> {
    
    @Query("SELECT obj FROM PasswordRecoverEntity obj WHERE obj.token = :token AND obj.expiration > :now")
    List<PasswordRecoverEntity> searchValidTokens(String token, Instant now);
}