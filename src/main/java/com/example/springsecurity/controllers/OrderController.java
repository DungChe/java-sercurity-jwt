package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.payload.request.OrderForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
public class OrderController {

    @Autowired
    private OrderService orderService;
// USER
    // Thực hiện đặt hàng
    @PostMapping("/orders/place")
    public ResponseEntity<ResponseData<OrderDto>> createOrder(@RequestBody OrderForm form) {
        ResponseData<OrderDto> response = orderService.createOrder(form);
        return ResponseEntity.ok(response);
    }

    // User cập nhật đơn hàng của mình
    @PutMapping("/orders/update/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> updateOrderForUser(@PathVariable Long orderId, @RequestBody OrderForm form) {
        ResponseData<OrderDto> response = orderService.updateOrderForUser(orderId, form);
        return ResponseEntity.ok(response);
    }

    // Hủy đơn hàng
    @PatchMapping("/orders/cancel/{orderId}")
    public ResponseEntity<ResponseData<String>> cancelOrder(@PathVariable Long orderId) {
        ResponseData<String> response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }

    // User xem chi tiết đơn hàng của họ
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> getOrderDetailsForUser(@PathVariable Long orderId) {
        ResponseData<OrderDto> response = orderService.getOrderDetailsForUser(orderId);
        return ResponseEntity.ok(response);
    }

    // User lấy danh sách các đơn hàng của họ
    @GetMapping("/orders")
    public ResponseEntity<ResponseData<List<OrderDto>>> getMyOrders() {
        ResponseData<List<OrderDto>> response = orderService.getMyOrder();
        return ResponseEntity.ok(response);
    }
}
