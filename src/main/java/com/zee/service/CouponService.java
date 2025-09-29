package com.zee.service;

import java.util.List;

import com.zee.model.Cart;
import com.zee.model.Coupon;
import com.zee.model.User;

public interface CouponService {
	Cart applyCoupon(String code, double orderValue, User user) throws Exception;
	Cart removeCoupon(String code,User user) throws Exception;
	Coupon findCouponById(Long id) throws Exception;
	Coupon createCoupon(Coupon coupon);
	List<Coupon> findAllCoupons();
	void deleteCoupon(Long id) throws Exception;
}
