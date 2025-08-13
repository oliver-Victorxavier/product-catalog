package com.victorxavier.product_catalog.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ProductDTO(Long id, String name, String description, BigDecimal price,
                         String imgUrl, Instant date, List<CategoryDTO> categories ) {

}
