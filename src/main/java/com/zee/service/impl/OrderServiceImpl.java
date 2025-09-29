package com.zee.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zee.dto.OrderStatus;
import com.zee.dto.PaymentStatus;
import com.zee.model.Address;
import com.zee.model.Cart;
import com.zee.model.CartItem;
import com.zee.model.Order;
import com.zee.model.OrderItem;
import com.zee.model.User;
import com.zee.repository.AddressRepository;
import com.zee.repository.OrderItemRepository;
import com.zee.repository.OrderRepository;
import com.zee.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

	private final OrderRepository orderRepository;
	private final AddressRepository addressRepository;
	private final OrderItemRepository orderItemRepository;
	
	@Override
	public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
		
		
		// here iam checking if user already have this address or not
		// if user already have this address then we will not add this address again
		// else we will add this address to user address list
		if(!user.getAddresses().contains(shippingAddress)){
			user.getAddresses().add(shippingAddress);
		}
		// saving address to address table
		Address address = addressRepository.save(shippingAddress);
		
		// multi vender platform
		// brand 1 => 4shirt
		// brand 2 => 3 pants
		// as we are ordering each product from diff seller therefrore we have to create seperate
		// order for each seller
		
		
		
		
		// grouping cart items by seller id 
		// so that we can create order for each seller seperately
		// here key is seller id and value is list of cart items
		// steam() is used to convert list to stream (stream is a sequence of elements)
		// collect() is used to collect the elements of stream and convert it to list or set or map
		// groupingBy() is used to group the elements of stream by a specific property
		// in our case we are grouping by seller id item.getProduct().getSeller().getId()
		Map<Long, List<CartItem>> itemsBySeller = cart.getCartItems().stream()
				.collect(Collectors.groupingBy(item-> 
				item.getProduct().getSeller().getId()));
		
		// now we will create order for each seller seperately
		Set<Order> orders = new HashSet<>();
		
		// iterating over the map
		for(Map.Entry<Long, List<CartItem>> entry: itemsBySeller.entrySet()) {
			
			Long sellerId = entry.getKey();
			List<CartItem> items = entry.getValue();
			
			// calculating total price and total items
			int totalOrderPrice = items.stream().mapToInt(
					CartItem::getSellingPrice).sum();
			// calculating total items
			int totalItems = items.stream().mapToInt(
					CartItem::getQuantity).sum();
			
			// creating order and setting all the details
			Order createdOrder = new Order();
			createdOrder.setUser(user);
			createdOrder.setSellerId(sellerId);
			createdOrder.setTotalMrpPrice(totalOrderPrice);
			createdOrder.setTotalItem(totalItems);
			createdOrder.setTotalSellingPrice(totalOrderPrice);
			createdOrder.setTotalItem(totalItems);
			createdOrder.setShippingAddress(shippingAddress);
			createdOrder.setOrderStatus(OrderStatus.PENDING);
			createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
			
			// saving order to order table
			Order savedOrder = orderRepository.save(createdOrder);
			// adding  tordero orders set
			orders.add(savedOrder);
			
			// now we will create order items for each cart item and save it to order item table
			List<OrderItem> orderItems = new ArrayList<>();
			
			for(CartItem item:items) {
				// creating order item and setting all the details
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(savedOrder);
				orderItem.setMrpPrice(item.getMrpPrice());
				orderItem.setProduct(item.getProduct());
				orderItem.setQuantity(item.getQuantity());
				orderItem.setSize(item.getSize());
				orderItem.setUserId(item.getUserId());
				orderItem.setSellingPrice(item.getSellingPrice());
				
				savedOrder.getOrderItems().add(orderItem);
				// saving order item to order item table
				OrderItem savedOrderItem = orderItemRepository.save(orderItem);
				// adding order item to order items list
				orderItems.add(savedOrderItem);
			}
		}
		
		
		return orders;
	}

	@Override
	public Order findOrderById(Long id) throws Exception {
		
		return orderRepository.findById(id).orElseThrow(()-> new Exception("order not found by given id"));
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {
		return orderRepository.findByUserId(userId);
	}

	@Override
	public List<Order> sellersOrder(Long sellerId) {
		return orderRepository.findBySellerId(sellerId);
	}

	@Override
	public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
		Order order = findOrderById(orderId);
		order.setOrderStatus(orderStatus);
		return orderRepository.save(order);
	}

	@Override
	public Order cancelOrder(Long orderId, User user) throws Exception {
		Order order = findOrderById(orderId);
		
		if(!user.getId().equals(order.getUser().getId())) {
			throw new Exception("You dont have access to this order");
		}
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}
	
	@Override
	public OrderItem getOrderItemById(Long id) throws Exception {
		return orderItemRepository.findById(id).orElseThrow(()-> new Exception("Order item not exists"));
	}

}
