package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.payload.request.OrderForm;
import com.example.springsecurity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Thực hiện đặt hàng
    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderForm form) {
        OrderDto order = orderService.placeOrder(form);
        return ResponseEntity.ok(order);
    }

    // User cập nhật đơn hàng của mình
    @PutMapping("/user/update/{orderId}")
    public ResponseEntity<OrderDto> updateOrderForUser(@PathVariable Long orderId, @RequestBody OrderForm form) {
        OrderDto updatedOrder = orderService.updateOrderForUser(orderId, form);
        return ResponseEntity.ok(updatedOrder);
    }

     // Hủy đơn hàng
    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {return ResponseEntity.ok(orderService.cancelOrder(orderId));}

    // User xem chi tiết đơn hàng của họ
    @GetMapping("/user/my-orders/{orderId}")
    public ResponseEntity<OrderDto> getOrderDetailsForUser(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderDetailsForUser(orderId);
        return ResponseEntity.ok(order);
    }

    // User lấy danh sách các đơn hàng của họ
    @GetMapping("/user/my-orders")
    public ResponseEntity<List<OrderDto>> getMyOrders() {
        List<OrderDto> orders = orderService.getMyOrder();
        return ResponseEntity.ok(orders);
    }
}
