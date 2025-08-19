package com.victorxavier.product_catalog.domain.exception;

/**
 * Exceção lançada quando as credenciais fornecidas são inválidas
 * Esta exceção pertence ao domínio pois representa uma regra de negócio
 */
public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}