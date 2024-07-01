package com.example.demo.service;

import com.example.demo.domain.Coupon;
import com.example.demo.domain.Product;
import com.example.demo.dto.CalculatePriceRequest;
import com.example.demo.dto.PurchaseRequest;
import com.example.demo.exception.CouponNotFoundException;
import com.example.demo.payment_processors.Paypal;
import com.example.demo.payment_processors.StripePaymentProcessor;
import com.example.demo.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FinalPriceServiceImplTest {

    @InjectMocks
    @Autowired
    private FinalPriceServiceImpl finalPriceService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CouponService couponService;
    @Mock
    private Paypal paypal;
    @Mock
    private StripePaymentProcessor stripePaymentProcessor;

    CalculatePriceRequest mockCalculatePriceRequest() {
        return CalculatePriceRequest.builder()
                .product(1L)
                .couponCode("P10")
                .taxNumber("DE123456789")
                .build();
    }

    PurchaseRequest mockPurchaseRequest() {
        return PurchaseRequest.builder()
                .product(1L)
                .couponCode("P10")
                .taxNumber("DE123456789")
                .paymentProcessor("paypal")
                .build();
    }

    Optional<Coupon> mockCouponPersist() {
        return Optional.of(Coupon
                .builder()
                .id(1L)
                .name("SALE")
                .couponCode("P10")
                .build());
    }

    Optional<Product> mockProductPersist() {
        return Optional.of(Product
                .builder()
                .id(1L)
                .name("Samsung")
                .price(BigDecimal.valueOf(100))
                .build());
    }

    @Test
    public void should_call_calculatePrice() {
        when(couponService.findCouponByCouponCode(anyString())).thenReturn(mockCouponPersist());
        when(productRepository.findById(1L)).thenReturn(mockProductPersist());

        BigDecimal price = finalPriceService.calculatePrice(mockCalculatePriceRequest());

        assertEquals(price, BigDecimal.valueOf(107.10).setScale(2));

        verify(couponService).findCouponByCouponCode(anyString());
        verify(productRepository).findById(anyLong());
    }

    @Test(expected = CouponNotFoundException.class)
    public void should_call_calculatePrice_throw_exception() {
        when(couponService.findCouponByCouponCode(anyString())).thenReturn(Optional.empty());
        when(productRepository.findById(1L)).thenReturn(mockProductPersist());
        BigDecimal price = finalPriceService.calculatePrice(mockCalculatePriceRequest());
    }

    @Test
    public void purchase() {
        when(couponService.findCouponByCouponCode(anyString())).thenReturn(mockCouponPersist());
        when(productRepository.findById(1L)).thenReturn(mockProductPersist());
        finalPriceService.purchase(mockPurchaseRequest());
    }
}