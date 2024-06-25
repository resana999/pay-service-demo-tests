package com.example.demo.service;

import com.example.demo.domain.Coupon;
import com.example.demo.repository.CouponRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Optional<Coupon> findCouponByCouponCode(String couponCode) {
        return couponRepository.findCouponByCouponCode(couponCode);
    }
}
