package com.victorxavier.product_catalog.domain.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String msg) {

        super(msg);
    }
    
    public InternalServerException(String msg, Throwable cause) {

        super(msg, cause);
    }
}