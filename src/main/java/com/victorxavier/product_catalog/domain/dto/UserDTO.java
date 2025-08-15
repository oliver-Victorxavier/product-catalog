package com.victorxavier.product_catalog.domain.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record UserDTO(
    String id,
    String firstName,
    String lastName,
    String email,
    String username,
    LocalDate birthDate,
    Instant creationTimestamp,
    Set<RoleDTO> roles
) {}