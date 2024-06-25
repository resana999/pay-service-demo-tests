package com.example.demo.repository;

import com.example.demo.domain.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponTypeRepository extends JpaRepository<CouponType, Long> {

}
