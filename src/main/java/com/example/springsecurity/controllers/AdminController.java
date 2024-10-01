package com.example.springsecurity.controllers;


import com.example.springsecurity.exception.ResourceNotFoundException;
import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.OrderForm;
import com.example.springsecurity.model.payload.UserForm;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.OrderService;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    // XEM
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() { return ResponseEntity.ok(userService.getAll());
    }
    // XÓA
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {return ResponseEntity.ok(userService.delete(id));
    }

    // SỬA
    @PutMapping("/users/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserForm form) { return ResponseEntity.ok(userService.update(id, form));
    }
    // XEM CHI TẾT
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {UserDto user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    // PHAN DON HANG
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() { return ResponseEntity.ok(orderService.getAll());
    }

    // Cập nhật đơn hàng ( PENDING, IN_PROGRESS, COMPLETED, CANElED) admin thuc hien
    @PutMapping("/orders/update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderForm form) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, form));
    }

    // Admin xem chi tiết đơn hàng
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> getOrderDetailsForAdmin(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderDetailsForAdmin(orderId);
        return ResponseEntity.ok(order);
    }

}
//
//    // SEARCH
//    @GetMapping("/search")
//    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String query) {List<UserDto> users = userService.searchUser(query);
//        return ResponseEntity.ok(users);
//    }

