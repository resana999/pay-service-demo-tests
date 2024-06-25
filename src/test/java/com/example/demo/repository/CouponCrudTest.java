package com.example.demo.repository;

import com.example.demo.domain.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CouponCrudTest {

    @Autowired
    CouponRepository couponRepository;

    @Before
    public void before() {
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setCouponCode("P10");
        coupon.setName("SUMMER_SALE");
        couponRepository.save(coupon);
    }

    @Test
    @Order(2)
    public void test_coupon_repository() {
        Coupon coupon = couponRepository.findCouponByCouponCode("P10").get();
        assertEquals(coupon.getName(), "SUMMER_SALE");
    }
}