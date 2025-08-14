package com.victorxavier.product_catalog.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
    @NotBlank(message = "Campo requerido")
    @Email(message = "Email inválido")
    String email
) {
}