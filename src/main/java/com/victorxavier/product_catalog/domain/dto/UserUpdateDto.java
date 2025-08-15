package com.victorxavier.product_catalog.domain.dto;

import com.victorxavier.product_catalog.domain.validation.UserUpdateValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserUpdateValid
public record UserUpdateDto(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username
) {
}