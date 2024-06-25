package com.example.demo.service;

import com.example.demo.domain.CountryTaxCode;
import com.example.demo.domain.Coupon;
import com.example.demo.domain.Product;
import com.example.demo.dto.CalculatePriceRequest;
import com.example.demo.dto.PurchaseRequest;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.UnknowTaxNumberException;
import com.example.demo.payment_processors.Paypal;
import com.example.demo.payment_processors.StripePaymentProcessor;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class FinalPriceServiceImpl implements FinalPriceService {

    private final ProductRepository productRepository;
    private final CouponService couponService;
    private final Paypal paypal;
    private final StripePaymentProcessor stripePaymentProcessor;

    @Override
    public BigDecimal calculatePrice(CalculatePriceRequest calculatePriceRequest) {
        Coupon coupon = couponService.findCouponByCouponCode(calculatePriceRequest.getCouponCode()).orElse(null);
        Product product = productRepository.findById(calculatePriceRequest.getProduct())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));


        BigDecimal prePriceWithCoupon = null;
        //label style :)
        ApplyCoupon:
        {
            if (coupon == null) break ApplyCoupon;
            long couponValue = Long.parseLong(coupon.getCouponCode().substring(1));
            switch (coupon.getCouponCode().substring(0, 1)) {
                case "F":
                    prePriceWithCoupon = product.getPrice().subtract(BigDecimal.valueOf(couponValue));
                case "P":
                    prePriceWithCoupon = product.getPrice().subtract(
                            product.getPrice().multiply(BigDecimal.valueOf(couponValue).divide(BigDecimal.valueOf(100L))));
            }
        }

        for (CountryTaxCode value : CountryTaxCode.values()) {
            if (Pattern.compile(value.pattern).matcher(calculatePriceRequest.getTaxNumber()).matches()) {
                return prePriceWithCoupon.add(prePriceWithCoupon.multiply(value.taxPercent.divide(BigDecimal.valueOf(100L)))).setScale(2);
            }
        }
        throw new UnknowTaxNumberException("Unknow tax number: " + calculatePriceRequest.getTaxNumber());
    }


    @Transactional
    @Override
    public void purchase(PurchaseRequest purchaseRequest) {
        switch (purchaseRequest.getPaymentProcessor()) {
            case "paypal":
                paypal.makePayment(
                        calculatePrice(CalculatePriceRequest.builder()
                                .product(purchaseRequest.getProduct())
                                .taxNumber(purchaseRequest.getTaxNumber())
                                .couponCode(purchaseRequest.getCouponCode())
                                .build()).setScale(2, RoundingMode.CEILING).intValue());
            case "stripe":
                stripePaymentProcessor.pay(
                        calculatePrice(CalculatePriceRequest.builder()
                                .product(purchaseRequest.getProduct())
                                .taxNumber(purchaseRequest.getTaxNumber())
                                .couponCode(purchaseRequest.getCouponCode())
                                .build()).floatValue());
        }

    }
}
