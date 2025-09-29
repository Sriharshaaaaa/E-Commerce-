package com.zee.service;

import java.util.List;
import java.util.Set;

import com.zee.dto.OrderStatus;
import com.zee.model.Address;
import com.zee.model.Cart;
import com.zee.model.Order;
import com.zee.model.OrderItem;
import com.zee.model.User;

public interface OrderService {
	Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
	Order findOrderById(Long id) throws Exception;
	List<Order> userOrderHistory(Long userId);
	List<Order> sellersOrder(Long sellerId);
	Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;
	Order cancelOrder(Long orderId, User user) throws Exception;
	OrderItem getOrderItemById(Long id) throws Exception;
}
 