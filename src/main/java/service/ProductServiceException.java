package service;

import java.io.Serial;

public class ProductServiceException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductServiceException(String message) {
        super(message);
    }
}