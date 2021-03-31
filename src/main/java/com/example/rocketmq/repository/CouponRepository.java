package com.example.rocketmq.repository;

import com.example.rocketmq.model.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    List<Coupon> findByOrderId(Integer orderId);

    List<Coupon> findByUserId(Integer userId);
}
