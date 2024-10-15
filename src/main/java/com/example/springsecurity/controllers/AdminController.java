package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.payload.request.ChangeStatusOrderForm;
import com.example.springsecurity.model.payload.request.OrderForm;
import com.example.springsecurity.model.payload.request.UserForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.OrderService;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final OrderService orderService;

    @Autowired
    private final UserRepository userRepository;

    // XEM TẤT CẢ NGƯỜI DÙNG
    @GetMapping("/users")
    public ResponseEntity<ResponseData<List<UserDto>>> getAllUsers() {
        ResponseData<List<UserDto>> response = userService.getAll();
        return ResponseEntity.ok(response);
    }

    // XÓA NGƯỜI DÙNG
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<ResponseData<String>> deleteUser(@PathVariable Long id) {
        ResponseData<String> response = userService.delete(id);
        return ResponseEntity.ok(response);
    }

    // SỬA NGƯỜI DÙNG
    @PutMapping("/users/update/{id}")
    public ResponseEntity<ResponseData<String>> updateUser(@PathVariable Long id, @RequestBody UserForm form) {
        ResponseData<String> response = userService.update(id, form);
        return ResponseEntity.ok(response);
    }

    // XEM CHI TIẾT NGƯỜI DÙNG
    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseData<UserDto>> getUserById(@PathVariable Long id) {
        ResponseData<UserDto> user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    // XEM TẤT CẢ ĐƠN HÀNG
    @GetMapping("/orders")
    public ResponseEntity<ResponseData<List<OrderDto>>> getAllOrders() {
        ResponseData<List<OrderDto>> response = orderService.getAll();
        return ResponseEntity.ok(response);
    }

    // CẬP NHẬT ĐƠN HÀNG
    @PutMapping("/orders/update/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> updateOrder(@PathVariable Long orderId, @RequestBody OrderForm form) {
        ResponseData<OrderDto> response = orderService.updateOrder(orderId, form);
        return ResponseEntity.ok(response);
    }

    // THAY ĐỔI TRẠNG THÁI ĐƠN HÀNG
    @PatchMapping("/orders/change-status/{orderId}")
    public ResponseEntity<ResponseData<String>> changeStatusOrder(@PathVariable Long orderId, @RequestBody ChangeStatusOrderForm form) {
        ResponseData<String> response = orderService.changeStatusOrder(orderId, form);
        return ResponseEntity.ok(response);
    }

    // ADMIN XEM CHI TIẾT ĐƠN HÀNG
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> getOrderDetailsForAdmin(@PathVariable Long orderId) {
        ResponseData<OrderDto> order = orderService.getOrderDetailsForAdmin(orderId);
        return ResponseEntity.ok(order);
    }
}
