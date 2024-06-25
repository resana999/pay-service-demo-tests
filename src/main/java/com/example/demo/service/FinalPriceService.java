package com.example.demo.service;

import com.example.demo.dto.CalculatePriceRequest;
import com.example.demo.dto.PurchaseRequest;

import java.math.BigDecimal;

public interface FinalPriceService {
    BigDecimal calculatePrice(CalculatePriceRequest calculatePriceRequest);
    void purchase(PurchaseRequest purchaseRequest);

}
