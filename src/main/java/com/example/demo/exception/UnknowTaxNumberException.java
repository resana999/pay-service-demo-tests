package com.example.demo.exception;

public class UnknowTaxNumberException extends RuntimeException {
    public UnknowTaxNumberException(String message) {
        super(message);
    }
}
