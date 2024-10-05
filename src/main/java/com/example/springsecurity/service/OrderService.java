package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.OrderForm;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();  // Lấy tất cả đơn hàng (admin)

    OrderDto placeOrder(OrderForm orderForm);  // Đặt hàng
    OrderDto updateOrder(Long orderId, OrderForm orderForm);  // Admin cập nhật đơn hàng
    OrderDto updateOrderForUser(Long orderId, OrderForm orderForm);  // Người dùng cập nhật đơn hàng của mình
    OrderDto cancelOrder(Long orderId);  // Hủy đơn hàng

    List<OrderDto> getMyOrder();  // Lấy đơn hàng của người dùng hiện tại

    OrderDto getOrderDetailsForAdmin(Long orderId);  // Admin xem chi tiết đơn hàng
    OrderDto getOrderDetailsForUser(Long orderId);  // Người dùng hiện tại xem chi tiết đơn hàng của họ
}
