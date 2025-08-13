package com.victorxavier.product_catalog.domain.projection;

/**
 * a generic interface for entities that have an identifier
 */
public interface IdProjection<E> {
    E getId();
}