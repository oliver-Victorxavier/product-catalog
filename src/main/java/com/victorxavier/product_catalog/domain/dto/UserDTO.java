package com.victorxavier.product_catalog.domain.dto;

import java.time.Instant;
import java.util.Set;

public record UserDTO(
        String id,
        String username,
        Instant creationTimestamp,
        Set<String> roles
) {}