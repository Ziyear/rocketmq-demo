package com.example.rocketmq.service;

import com.example.rocketmq.common.CouponStatusEnum;
import com.example.rocketmq.model.entity.Coupon;
import com.example.rocketmq.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 功能描述 : TODO
 *
 * @author Ziyear 2021-03-31 21:02
 */
@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public void sendNewerCoupon(Integer userId) {
        // 模拟耗时操作
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Coupon coupon = new Coupon();
        coupon.setUserId(userId);
        coupon.setFullTotalAmount(new BigDecimal("100"));
        coupon.setFullUseAmount(new BigDecimal("50"));
        coupon.setStatus(CouponStatusEnum.UNUSED.getCode());
        couponRepository.save(coupon);
    }
}
