package com.example.demo.payment_processors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class StripePaymentProcessor {

    private final float LIMIT = 100;

    public boolean pay(Float payment) {
        return !(payment < LIMIT);
    }
}
