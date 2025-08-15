package com.victorxavier.product_catalog.domain.dto;

import java.time.LocalDate;
import java.util.Set;

public record UserUpdateDto(
    String firstName,
    String lastName,
    String email,
    LocalDate birthDate,
    Set<String> roleIds
) {}