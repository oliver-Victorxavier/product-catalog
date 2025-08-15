package com.victorxavier.product_catalog.domain.dto;

import java.time.LocalDate;
import java.util.Set;

public record UserInsertDTO(
    String firstName,
    String lastName,
    String email,
    String username,
    String password,
    LocalDate birthDate,
    Set<String> roleIds
) {}