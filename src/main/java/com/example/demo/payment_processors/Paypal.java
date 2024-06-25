package com.example.demo.payment_processors;

import org.springframework.stereotype.Component;

@Component
public class Paypal {

    private final int LIMIT = 100 * 1000;

    public void makePayment(Integer payment) {
        if (payment > LIMIT) {
            throw new PaymentLimitException(String.format("Payment limit must less than %d", LIMIT));
        }
    }
}
