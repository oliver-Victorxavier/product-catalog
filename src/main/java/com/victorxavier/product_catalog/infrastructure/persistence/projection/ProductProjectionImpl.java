package com.victorxavier.product_catalog.infrastructure.persistence.projection;

import java.math.BigDecimal;
import java.time.Instant;

public interface ProductProjectionImpl {
    Long getId();
    String getName();
    BigDecimal getPrice();
    Instant getDate();
    String getDescription();
    String getImgUrl();
}
