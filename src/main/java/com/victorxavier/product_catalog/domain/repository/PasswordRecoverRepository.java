package com.victorxavier.product_catalog.domain.repository;

import com.victorxavier.product_catalog.domain.entity.PasswordRecover;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PasswordRecoverRepository {
    PasswordRecover save(PasswordRecover passwordRecover);
    Optional<PasswordRecover> findById(Long id);
    List<PasswordRecover> searchValidTokens(String token, Instant now);
    void deleteById(Long id);
}