package com.example.demo.exception;

public class UnknowCouponTypeException extends RuntimeException {
    public UnknowCouponTypeException(String message) {
        super(message);
    }
}
