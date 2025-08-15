package com.victorxavier.product_catalog.domain.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String msg) {
        super(msg);
    }

    public ConflictException(String msg, Throwable cause) {
        super(msg, cause);
    }
}