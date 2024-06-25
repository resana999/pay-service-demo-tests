package com.example.demo.payment_processors;

public class PaymentLimitException extends RuntimeException{
    public PaymentLimitException(String message) {
        super(message);
    }
}
