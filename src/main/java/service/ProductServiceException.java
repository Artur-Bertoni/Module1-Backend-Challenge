package service;

import javax.swing.*;

public class ProductServiceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    //Constructors
    public ProductServiceException(String message) {
        super(message);
    }
}