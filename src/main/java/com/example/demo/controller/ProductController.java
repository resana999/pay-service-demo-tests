package com.example.demo.controller;

import com.example.demo.dto.CalculatePriceRequest;
import com.example.demo.dto.PurchaseRequest;
import com.example.demo.service.FinalPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Api("Calculate controller")
public class ProductController {

    @Autowired
    FinalPriceService paymentService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @ApiOperation("POST: для расчёта цены")
    @PostMapping("/calculate-price")
    public BigDecimal calculatePrice(
            @ApiParam("CalculatePriceRequest") @RequestBody CalculatePriceRequest calculatePriceRequest) {
        return paymentService.calculatePrice(calculatePriceRequest);
    }

    @ApiOperation("POST: для осуществления покупки")
    @PostMapping("/purchase")
    public void purchase(@RequestBody PurchaseRequest purchaseRequest) {
        paymentService.purchase(purchaseRequest);
    }
}
