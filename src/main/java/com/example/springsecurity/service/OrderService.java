package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.payload.request.ChangeStatusOrderForm;
import com.example.springsecurity.model.payload.request.OrderForm;
import com.example.springsecurity.model.payload.response.ResponseData;

import java.util.List;

public interface OrderService {
    ResponseData<List<OrderDto>> getAll();  // Lấy tất cả đơn hàng (admin)

    ResponseData<OrderDto> createOrder(OrderForm orderForm);  // Đặt hàng
    ResponseData<OrderDto> updateOrder(Long orderId, OrderForm orderForm);  // Admin cập nhật đơn hàng
    ResponseData<String> changeStatusOrder(Long orderID, ChangeStatusOrderForm form);  // Thay đổi trạng thái đơn hàng
    ResponseData<OrderDto> updateOrderForUser(Long orderId, OrderForm orderForm);  // Người dùng cập nhật đơn hàng của mình
    ResponseData<String> cancelOrder(Long orderId);  // Hủy đơn hàng
    ResponseData<List<OrderDto>> getMyOrder();  // Lấy đơn hàng của người dùng hiện tại

    ResponseData<OrderDto> getOrderDetailsForAdmin(Long orderId);  // Admin xem chi tiết đơn hàng
    ResponseData<OrderDto> getOrderDetailsForUser(Long orderId);  // Người dùng hiện tại xem chi tiết đơn hàng của họ
}
