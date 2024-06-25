package com.example.demo.repository;

import com.example.demo.domain.Coupon;
import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findById(Long id);

    Optional<Coupon> findCouponByCouponCode(String couponType);
}
